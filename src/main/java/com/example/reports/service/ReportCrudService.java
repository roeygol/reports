package com.example.reports.service;

import com.example.reports.domain.Report;

import java.util.List;

public interface ReportCrudService {

    List<Report> findAll();

    Report findById(String reportName);

    Report create(Report report);

    Report update(String reportName, Report report);

    void delete(String reportName);
}
