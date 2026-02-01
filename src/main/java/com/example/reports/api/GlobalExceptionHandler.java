package com.example.reports.api;

import com.example.reports.api.dto.ErrorResponse;
import com.example.reports.exception.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidReportRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidReportRequest(InvalidReportRequestException ex) {
        return buildError(HttpStatus.BAD_REQUEST, "INVALID_REPORT_REQUEST", ex.getMessage());
    }

    @ExceptionHandler(ReportNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReportNotFound(ReportNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, "REPORT_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(ReportConfigurationException.class)
    public ResponseEntity<ErrorResponse> handleReportConfiguration(ReportConfigurationException ex) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "REPORT_CONFIGURATION_ERROR", ex.getMessage());
    }

    @ExceptionHandler(ReportQueryExecutionException.class)
    public ResponseEntity<ErrorResponse> handleReportQuery(ReportQueryExecutionException ex) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "REPORT_QUERY_EXECUTION_ERROR", ex.getMessage());
    }

    @ExceptionHandler(CSVGenerationException.class)
    public ResponseEntity<ErrorResponse> handleCSVGeneration(CSVGenerationException ex) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "CSV_GENERATION_ERROR", ex.getMessage());
    }

    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<ErrorResponse> handleMailSend(MailSendException ex) {
        return buildError(HttpStatus.BAD_GATEWAY, "MAIL_SEND_ERROR", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .orElse("Validation failed");
        return buildError(HttpStatus.BAD_REQUEST, "INVALID_REPORT_REQUEST", message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        return buildError(HttpStatus.BAD_REQUEST, "INVALID_REPORT_REQUEST", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "UNEXPECTED_ERROR", ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildError(HttpStatus status, String errorCode, String message) {
        ErrorResponse body = new ErrorResponse(errorCode, message, Instant.now());
        return ResponseEntity.status(status).body(body);
    }
}

