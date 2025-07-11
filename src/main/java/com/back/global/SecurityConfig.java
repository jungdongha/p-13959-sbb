package com.back.global;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher; // <-- 이 임포트는 더 이상 필요 없다.

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        // AntPathRequestMatcher 대신 람다 표현식으로 직접 경로를 지정한다.
                        // "/**"는 모든 경로를 의미한다.
                        .requestMatchers("/**").permitAll()) // <-- AntPathRequestMatcher 제거 및 경로 직접 지정
                .csrf((csrf) -> csrf
                        // AntPathRequestMatcher 대신 람다 표현식으로 직접 경로를 지정한다.
                        .ignoringRequestMatchers("/h2-console/**")) // <-- AntPathRequestMatcher 제거 및 경로 직접 지정
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
        ;
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}