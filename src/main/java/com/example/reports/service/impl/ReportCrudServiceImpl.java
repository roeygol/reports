package com.example.reports.service.impl;

import com.example.reports.domain.Report;
import com.example.reports.exception.ReportAlreadyExistsException;
import com.example.reports.exception.ReportNotFoundException;
import com.example.reports.repository.ReportRepository;
import com.example.reports.service.ReportCrudService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportCrudServiceImpl implements ReportCrudService {

    private final ReportRepository repository;

    public ReportCrudServiceImpl(ReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Report> findAll() {
        return repository.findAll();
    }

    @Override
    public Report findById(String reportId) {
        return repository.findById(reportId)
                .orElseThrow(() -> new ReportNotFoundException(reportId));
    }

    @Override
    public Report create(Report report) {
        try {
            return repository.save(report);
        } catch (DuplicateKeyException e) {
            throw new ReportAlreadyExistsException(report.getReportName());
        }
    }

    @Override
    public Report update(String reportId, Report report) {
        Report existing = repository.findById(reportId)
                .orElseThrow(() -> new ReportNotFoundException(reportId));
        existing.setReportName(report.getReportName());
        existing.setQuery(report.getQuery());
        existing.setTemplateId(report.getTemplateId());
        existing.setTargetAddress(report.getTargetAddress());
        existing.setFileType(report.getFileType());
        existing.setEmailSubject(report.getEmailSubject());
        existing.setEmailBody1(report.getEmailBody1());
        existing.setEmailBody2(report.getEmailBody2());
        existing.setFeatureName(report.getFeatureName());
        try {
            return repository.save(existing);
        } catch (DuplicateKeyException e) {
            throw new ReportAlreadyExistsException(report.getReportName());
        }
    }

    @Override
    public void delete(String reportId) {
        if (!repository.existsById(reportId)) {
            throw new ReportNotFoundException(reportId);
        }
        repository.deleteById(reportId);
    }
}
