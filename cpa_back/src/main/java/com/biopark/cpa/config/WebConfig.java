package com.biopark.cpa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors.originFront}")
    private String corsOriginFront;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var allowedOriginsFront = corsOriginFront.split(",");

        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins(allowedOriginsFront)
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
