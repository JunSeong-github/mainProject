package com.example.mainproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class WebCorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration c = new CorsConfiguration();

        // 개발용: 모든 오리진 허용. (쿠키/자격증명 안 쓸 때)
        c.setAllowedOriginPatterns(List.of("*"));
        c.setAllowCredentials(false);

        c.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        c.setAllowedHeaders(List.of("*"));
        c.setExposedHeaders(List.of("Authorization","Location","Content-Disposition"));
        c.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", c);
        return source;
    }

    // ✅ 만약 프런트에서 쿠키(credential) 필요하면 위 대신 아래처럼:
    // @Bean
    // public CorsConfigurationSource corsConfigurationSource() {
    //     CorsConfiguration c = new CorsConfiguration();
    //     c.setAllowedOriginPatterns(List.of("http://localhost:5173","http://127.0.0.1:5173"));
    //     c.setAllowCredentials(true); // credentials 허용 시 * 불가 → 구체 오리진만
    //     c.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
    //     c.setAllowedHeaders(List.of("*"));
    //     c.setExposedHeaders(List.of("Authorization","Location","Content-Disposition"));
    //     c.setMaxAge(3600L);
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", c);
    //     return source;
    // }
}
