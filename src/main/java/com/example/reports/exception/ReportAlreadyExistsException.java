package com.example.reports.exception;

public class ReportAlreadyExistsException extends RuntimeException {

    public ReportAlreadyExistsException(String reportName) {
        super("Report '" + reportName + "' already exists");
    }
}
