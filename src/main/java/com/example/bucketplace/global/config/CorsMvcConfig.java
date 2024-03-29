package com.example.bucketplace.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

        corsRegistry.addMapping("/**")
                .exposedHeaders("Set-Cookie")
                .allowedOrigins(
                        "http://localhost:3000", "http://localhost:5500", "http://localhost:5000",
                        "http://127.0.0.1:3000", "http://127.0.0.1:5500", "http://127.0.0.1:5000",
                        "https://js-teal.vercel.app", "https://today-home-4-fe.vercel.app", "http://hanghae-4.ap-northeast-2.elasticbeanstalk.com"
                );
    }
}
