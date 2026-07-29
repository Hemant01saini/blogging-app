package com.blogapp.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/uploads/**") // iska matlab sirf itna hai:
//        Agar URL /uploads se start ho
//        to
//Controller mat dhundho.

                .addResourceLocations(
                        "file:uploads/"); // Disk me uploads folder ke andar file dhundho.
    }  // file:  iska mtlb "Computer ke folder se uthao."


}
