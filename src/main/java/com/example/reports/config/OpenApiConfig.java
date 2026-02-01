package com.example.reports.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Reports Execution API",
                version = "1.0.0",
                description = "API for executing predefined DB2-based reports and sending them via email"
        )
)
public class OpenApiConfig {
}

