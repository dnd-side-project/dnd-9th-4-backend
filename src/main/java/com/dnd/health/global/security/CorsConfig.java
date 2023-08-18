package com.dnd.health.global.security;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);

        config.addAllowedOrigin("https://dnd-9th-4-newple-app.vercel.app");
        config.addAllowedOrigin("https://dnd-9th-4-newple-app.vercel.app/");
        config.addAllowedOrigin("https://accounts.kakao.com/login");

        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PATCH");
        config.addAllowedMethod("DELETE");

        config.addAllowedHeader("*");
        config.setExposedHeaders(List.of("*"));

        source.registerCorsConfiguration("/**", config);
        return source;
    }
}