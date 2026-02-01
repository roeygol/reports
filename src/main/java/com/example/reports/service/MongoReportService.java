package com.example.reports.service;

import com.example.reports.domain.Report;

public interface MongoReportService {

    Report getReport(String reportName);
}

