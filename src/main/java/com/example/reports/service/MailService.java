package com.example.reports.service;

public interface MailService {

    void sendReport(String templateId,
                    String mailingListName,
                    byte[] attachment,
                    String attachmentFileName);
}

