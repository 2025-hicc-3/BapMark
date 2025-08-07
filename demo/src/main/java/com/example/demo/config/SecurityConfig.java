package com.example.demo.config;

import com.example.demo.jwt.JwtAuthenticationFilter;
import com.example.demo.jwt.JwtProvider;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserRepository userRepository) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",                    // 로그인 관련은 허용
                                "/v3/api-docs/**",                 // Swagger 문서
                                "/swagger-ui/**",                 // Swagger UI
                                "/swagger-ui.html",               // Swagger HTML 진입점
                                "/openapi/**",              // 우리가 설정한 API docs 커스텀 경로
                                "/docs/**",                 // 우리가 커스터마이징한 Swagger UI 경로
                                "/swagger-resources/**",          // Swagger 리소스
                                "/webjars/**"                     // Swagger 스타일/스크립트
                        ).permitAll()
                        .anyRequest().authenticated()           // 나머지는 인증 필요
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider, userRepository), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
