package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mapea las URL /uploads/** a la carpeta física "uploads/" (fuera de src)
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}