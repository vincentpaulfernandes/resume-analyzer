package com.vincent.resumeanalyzer.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Resume Analyzer API",
                version = "1.0",
                description = "API for analyzing resumes using AI"
        )
)
public class SwaggerConfig {
}