package com.example.reports.api;

import com.example.reports.api.dto.ExecuteReportRequest;
import com.example.reports.api.dto.ExecuteReportResponse;
import com.example.reports.service.ReportsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/reports")
public class ReportExecutionController {

    private final ReportsService reportsService;

    public ReportExecutionController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    @Operation(
            summary = "Execute a predefined report",
            description = "Executes the report identified by reportName, generates CSV, and emails the result."
    )
    @ApiResponse(responseCode = "200", description = "Report executed successfully",
            content = @Content(schema = @Schema(implementation = ExecuteReportResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
    @ApiResponse(responseCode = "404", description = "Report not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @ApiResponse(responseCode = "502", description = "Upstream mail provider failure", content = @Content)
    @PostMapping("/execute")
    public ResponseEntity<ExecuteReportResponse> executeReport(@Valid @RequestBody ExecuteReportRequest request) {
        reportsService.executeReport(request.getReportName());
        ExecuteReportResponse response = new ExecuteReportResponse(
                true,
                request.getReportName(),
                Instant.now()
        );
        return ResponseEntity.ok(response);
    }
}

