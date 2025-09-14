//package com.example.mainproject.common;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig_bak {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .cors(Customizer.withDefaults())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // CORS 프리플라이트
//                        .requestMatchers("/api/**").permitAll()                // 우리 API 전부 허용
//                        .anyRequest().permitAll()                              // 필요시 authenticated()로 변경
//                )
//                .formLogin(form -> form.disable())  // /login 리다이렉트 방지
//                .logout(logout -> logout.disable())
//                .httpBasic(Customizer.withDefaults());
//        return http.build();
//    }
//}