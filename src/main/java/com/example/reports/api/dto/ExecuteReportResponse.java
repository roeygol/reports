package com.example.reports.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Execution result of a report")
public class ExecuteReportResponse {

    @Schema(description = "Indicates whether execution completed without error")
    private final boolean success;

    @Schema(description = "Report name that was executed")
    private final String reportName;

    @Schema(description = "Execution timestamp (UTC)")
    private final Instant executionTimestamp;

    public ExecuteReportResponse(boolean success, String reportName, Instant executionTimestamp) {
        this.success = success;
        this.reportName = reportName;
        this.executionTimestamp = executionTimestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getReportName() {
        return reportName;
    }

    public Instant getExecutionTimestamp() {
        return executionTimestamp;
    }
}

