package com.rental.haedal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호를 비활성화 (H2 Console 사용 시 필요)
                .csrf(csrf -> csrf.disable())
                // H2 Console을 iframe으로 접근할 수 있도록 frameOptions 비활성화
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .authorizeHttpRequests(auth -> auth
                        // H2 Console 경로에 대해 모두 허용
                        .requestMatchers("/h2-console/**", "/swagger",
                                "/swagger-ui.html", "/swagger-ui/**", "/api-docs",
                                "/api-docs/**", "/v3/api-docs/**").permitAll()
                        // 그 외의 요청은 인증 필요 (개발 환경에 따라 조정)
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}