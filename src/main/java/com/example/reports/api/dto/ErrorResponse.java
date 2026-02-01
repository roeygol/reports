package com.example.reports.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Standard error response")
public class ErrorResponse {

    @Schema(description = "Machine-readable error code", example = "REPORT_NOT_FOUND")
    private final String errorCode;

    @Schema(description = "Human-readable error message")
    private final String message;

    @Schema(description = "Timestamp of the error (UTC)")
    private final Instant timestamp;

    public ErrorResponse(String errorCode, String message, Instant timestamp) {
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}

