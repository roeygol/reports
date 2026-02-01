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
    public Report findById(String reportName) {
        return repository.findById(reportName)
                .orElseThrow(() -> new ReportNotFoundException(reportName));
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
    public Report update(String reportName, Report report) {
        Report existing = repository.findById(reportName)
                .orElseThrow(() -> new ReportNotFoundException(reportName));
        existing.setQuery(report.getQuery());
        existing.setTemplateId(report.getTemplateId());
        existing.setMailingListName(report.getMailingListName());
        return repository.save(existing);
    }

    @Override
    public void delete(String reportName) {
        if (!repository.existsById(reportName)) {
            throw new ReportNotFoundException(reportName);
        }
        repository.deleteById(reportName);
    }
}
