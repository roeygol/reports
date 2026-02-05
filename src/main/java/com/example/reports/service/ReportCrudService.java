package com.example.reports.service;

import com.example.reports.domain.Report;

import java.util.List;

public interface ReportCrudService {

    List<Report> findAll();

    Report findById(String reportId);

    Report create(Report report);

    Report update(String reportId, Report report);

    void delete(String reportId);
}
