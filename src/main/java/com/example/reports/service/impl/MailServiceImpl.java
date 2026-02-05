package com.example.reports.service.impl;

import com.example.reports.config.MailServiceProperties;
import com.example.reports.exception.MailSendException;
import com.example.reports.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    private final MailServiceProperties properties;
    private final RestTemplate restTemplate;

    public MailServiceImpl(MailServiceProperties properties, RestTemplate restTemplate) {
        this.properties = properties;
        this.restTemplate = restTemplate;
    }

    @Override
    @Async
    public void sendReport(String templateId,
                           String mailingListName,
                           byte[] attachment,
                           String attachmentFileName) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            if (properties.getApiKey() != null && !properties.getApiKey().isBlank()) {
                headers.set("Authorization", "Bearer " + properties.getApiKey());
            }

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("templateId", templateId);
            body.add("mailingListName", mailingListName);
            body.add("attachmentFileName", attachmentFileName);
            body.add("attachment", new ByteArrayResource(attachment) {
                @Override
                public String getFilename() {
                    return attachmentFileName;
                }
            });

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(
                    properties.getUrl(),
                    requestEntity,
                    String.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new MailSendException("Mail service returned " + response.getStatusCode(), null);
            }

            log.info("Mail for report '{}' sent to mailing list '{}' via REST service", attachmentFileName, mailingListName);
        } catch (MailSendException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to send email for report {}", attachmentFileName, ex);
            throw new MailSendException("Failed to send email for report", ex);
        }
    }
}
