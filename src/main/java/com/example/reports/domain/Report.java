package com.example.reports.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "reports")
public class Report {

    @Id
    private String reportId;

    @NotBlank
    @Size(max = 100)
    private String reportName;

    @NotBlank
    private String query;

    @Size(max = 36)
    private String templateId;

    @Size(max = 10)
    private List<@Size(max = 500) String> targetAddress;

    @NotNull
    private ReportFileType fileType;

    @Size(max = 200)
    private String emailSubject;

    private String emailBody1;

    private String emailBody2;

    @Size(max = 500)
    private String featureName;

    // Getters and Setters

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public List<String> getTargetAddress() {
        return targetAddress;
    }

    public void setTargetAddress(List<String> targetAddress) {
        this.targetAddress = targetAddress;
    }

    public ReportFileType getReportFileType() {
        return fileType;
    }

    public void setReportFileType(ReportFileType fileType) {
        this.fileType = fileType;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailBody1() {
        return emailBody1;
    }

    public void setEmailBody1(String emailBody1) {
        this.emailBody1 = emailBody1;
    }

    public String getEmailBody2() {
        return emailBody2;
    }

    public void setEmailBody2(String emailBody2) {
        this.emailBody2 = emailBody2;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }
}
