package com.example.reports.api;

import com.example.reports.api.dto.ReportRequest;
import com.example.reports.api.dto.ReportUpdateRequest;
import com.example.reports.domain.Report;
import com.example.reports.service.ReportCrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report-collection")
@Tag(name = "Report Collection", description = "CRUD operations for the report collection")
public class ReportCrudController {

    private final ReportCrudService reportCrudService;

    public ReportCrudController(ReportCrudService reportCrudService) {
        this.reportCrudService = reportCrudService;
    }

    @Operation(summary = "List all reports")
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping
    public ResponseEntity<List<Report>> findAll() {
        return ResponseEntity.ok(reportCrudService.findAll());
    }

    @Operation(summary = "Get report by name")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "404", description = "Report not found")
    @GetMapping("/{reportName}")
    public ResponseEntity<Report> findById(@PathVariable String reportName) {
        return ResponseEntity.ok(reportCrudService.findById(reportName));
    }

    @Operation(summary = "Create a new report")
    @ApiResponse(responseCode = "201", description = "Report created")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "409", description = "Report already exists")
    @PostMapping
    public ResponseEntity<Report> create(@Valid @RequestBody ReportRequest request) {
        Report report = toReport(request);
        Report created = reportCrudService.create(report);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update an existing report")
    @ApiResponse(responseCode = "200", description = "Report updated")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "404", description = "Report not found")
    @PutMapping("/{reportName}")
    public ResponseEntity<Report> update(
            @PathVariable String reportName,
            @Valid @RequestBody ReportUpdateRequest request) {
        Report report = toReport(reportName, request);
        return ResponseEntity.ok(reportCrudService.update(reportName, report));
    }

    @Operation(summary = "Delete a report")
    @ApiResponse(responseCode = "204", description = "Report deleted")
    @ApiResponse(responseCode = "404", description = "Report not found")
    @DeleteMapping("/{reportName}")
    public ResponseEntity<Void> delete(@PathVariable String reportName) {
        reportCrudService.delete(reportName);
        return ResponseEntity.noContent().build();
    }

    private Report toReport(ReportRequest request) {
        Report report = new Report();
        report.setReportName(request.getReportName());
        report.setQuery(request.getQuery());
        report.setTemplateId(request.getTemplateId());
        report.setMailingListName(request.getMailingListName());
        return report;
    }

    private Report toReport(String reportName, ReportUpdateRequest request) {
        Report report = new Report();
        report.setReportName(reportName);
        report.setQuery(request.getQuery());
        report.setTemplateId(request.getTemplateId());
        report.setMailingListName(request.getMailingListName());
        return report;
    }
}
