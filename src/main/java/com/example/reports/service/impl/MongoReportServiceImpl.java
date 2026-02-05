package com.example.reports.service.impl;

import com.example.reports.domain.Report;
import com.example.reports.exception.ReportNotFoundException;
import com.example.reports.repository.ReportRepository;
import com.example.reports.service.MongoReportService;
import org.springframework.stereotype.Service;

@Service
public class MongoReportServiceImpl implements MongoReportService {

    private final ReportRepository repository;

    public MongoReportServiceImpl(ReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public Report getReportByName(String reportName) {
        return repository.findByReportName(reportName)
                .orElseThrow(() -> new ReportNotFoundException(reportName));
    }
}
