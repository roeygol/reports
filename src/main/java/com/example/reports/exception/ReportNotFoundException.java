package com.example.reports.exception;

public class ReportNotFoundException extends RuntimeException {

    public ReportNotFoundException(String reportName) {
        super("Report '" + reportName + "' was not found");
    }
}

