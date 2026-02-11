package com.epam.jmp.rest_api_homework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppVersionConfig implements WebMvcConfigurer {

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        configurer
                // 1. Default Behavior (Safety Net)
                //  .setDefaultVersion("2.0")
                .setVersionRequired(true)
                .usePathSegment(1);            // Path: /api/v1/...
                // .useRequestHeader("X-API-Version")     // Header: X-API-Version: 1
                // .useQueryParam("version")              // Query: ?version=1
                // .useMediaTypeParameter(MediaType.APPLICATION_JSON, "version")  // Accept: ...; version=1
                // OR
                // .useMediaTypeParameter(MediaType.parseMediaType("application/vnd.api+json"), "version")

    }
}
