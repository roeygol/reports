package com.example.reports.service.impl;

import com.example.reports.domain.Report;
import com.example.reports.exception.InvalidReportRequestException;
import com.example.reports.exception.ReportConfigurationException;
import com.example.reports.service.CSVService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import com.example.reports.service.DB2Service;
import com.example.reports.service.MailService;
import com.example.reports.service.MongoReportService;
import com.example.reports.service.ReportsService;
import com.example.reports.service.model.DB2QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReportsServiceImpl implements ReportsService {

    private static final Logger log = LoggerFactory.getLogger(ReportsServiceImpl.class);

    private static final String REPORT_NAME_PATTERN = "^[a-zA-Z0-9_-]+$";

    private final MongoReportService mongoReportService;
    private final DB2Service db2Service;
    private final CSVService csvService;
    private final MailService mailService;
    private final Validator validator;

    public ReportsServiceImpl(MongoReportService mongoReportService,
                              DB2Service db2Service,
                              CSVService csvService,
                              MailService mailService,
                              Validator validator) {
        this.mongoReportService = mongoReportService;
        this.db2Service = db2Service;
        this.csvService = csvService;
        this.mailService = mailService;
        this.validator = validator;
    }

    @Override
    public void executeReport(String reportName) {
        long start = System.currentTimeMillis();
        boolean success = false;
        try {
            validateReportName(reportName);

            Report report = mongoReportService.getReport(reportName);
            validateReport(report);

            DB2QueryResult result = db2Service.executeQuery(report.getQuery());

            if (result.isEmpty()) {
                log.warn("Report '{}' returned empty result set", reportName);
            }

            byte[] csvBytes = csvService.generateCSV(result);

            String attachmentFileName = reportName + ".csv";
            mailService.sendReport(
                    report.getTemplateId(),
                    report.getMailingListName(),
                    csvBytes,
                    attachmentFileName
            );

            success = true;
        } finally {
            long durationMs = System.currentTimeMillis() - start;
            log.info("Report '{}' execution finished. success={}, durationMs={}",
                    reportName, success, durationMs);
        }
    }

    private void validateReportName(String reportName) {
        if (!StringUtils.hasText(reportName)) {
            throw new InvalidReportRequestException("reportName must not be null or blank");
        }
        String trimmed = reportName.trim();
        if (trimmed.length() < 3 || trimmed.length() > 100) {
            throw new InvalidReportRequestException("reportName length must be between 3 and 100");
        }
        if (!trimmed.matches(REPORT_NAME_PATTERN)) {
            throw new InvalidReportRequestException("reportName has invalid format");
        }
    }

    private void validateReport(Report report) {
        if (report == null) {
            throw new ReportConfigurationException("Report is null");
        }
        Set<ConstraintViolation<Report>> violations = validator.validate(report);
        if (!violations.isEmpty()) {
            String message = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining("; "));
            throw new ReportConfigurationException(message);
        }
    }
}

