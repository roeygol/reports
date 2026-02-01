package com.example.reports.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(MailServiceProperties mailServiceProperties) {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(mailServiceProperties.getTimeoutSeconds()))
                .setReadTimeout(Duration.ofSeconds(mailServiceProperties.getTimeoutSeconds()))
                .build();
    }
}
