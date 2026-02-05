package com.example.reports.service;

import java.util.List;

public interface MailService {

    void sendReport(String templateId,
                    List<String> targetAddress,
                    String emailSubject,
                    String emailBody1,
                    String emailBody2,
                    byte[] attachment,
                    String attachmentFileName);
}
