package com.example.reports.api.dto;

import jakarta.validation.constraints.NotBlank;

public class ReportUpdateRequest {

    @NotBlank
    private String query;

    @NotBlank
    private String templateId;

    @NotBlank
    private String mailingListName;

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

    public String getMailingListName() {
        return mailingListName;
    }

    public void setMailingListName(String mailingListName) {
        this.mailingListName = mailingListName;
    }
}
