package com.back.global;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration // 이 클래스가 스프링 설정 클래스임을 나타낸다.
@EnableWebSecurity // 스프링 시큐리티를 활성화하고 웹 보안 설정을 구성할 수 있도록 한다.
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean // 이 메서드가 스프링 빈을 생성함을 나타낸다.
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // HTTP 요청에 대한 접근 권한을 설정한다.
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        // 모든 경로("/**")에 대한 요청을 허용한다.
                        // 스프링 시큐리티 6.x부터 권장되는 방식이다.
                        .requestMatchers("/**").permitAll())
                // CSRF(Cross-Site Request Forgery) 보호 설정을 한다.
                .csrf((csrf) -> csrf
                        // 특정 경로("/h2-console/**")에 대한 CSRF 보호를 비활성화한다.
                        // H2 콘솔은 일반적으로 CSRF 토큰 없이 접근하므로 예외 처리한다.
                        .ignoringRequestMatchers("/h2-console/**"))
                // HTTP 헤더 관련 설정을 한다.
                .headers((headers) -> headers
                        // X-Frame-Options 헤더를 추가하여 클릭재킹 공격을 방지한다.
                        // SAMEORIGIN은 동일 출처의 프레임 내에서만 페이지를 렌더링하도록 허용한다.
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                // 폼 로그인 설정을 한다.
                .formLogin((formLogin) -> formLogin
                        // 로그인 페이지 경로를 설정한다.
                        .loginPage("/user/login")
                        // 로그인 성공 시 기본 리다이렉트 경로를 설정한다.
                        .defaultSuccessUrl("/"))
                // 로그아웃 설정을 한다.
                .logout((logout) -> logout
                        // 로그아웃 요청 경로를 설정한다.
                        // logoutRequestMatcher 대신 logoutUrl을 사용하여 문자열 경로를 직접 지정한다.
                        // 이 방식이 최신 스프링 시큐리티에서 권장되는 방식이다.
                        .logoutUrl("/user/logout")
                        // 로그아웃 성공 시 리다이렉트 경로를 설정한다.
                        .logoutSuccessUrl("/")
                        // 로그아웃 시 HTTP 세션을 무효화한다.
                        .invalidateHttpSession(true))
        ;
        // 설정된 HttpSecurity 객체를 사용하여 SecurityFilterChain을 빌드하고 반환한다.
        return http.build();
    }

    @Bean // 이 메서드가 스프링 빈을 생성함을 나타낸다.
    PasswordEncoder passwordEncoder() {
        // 비밀번호를 안전하게 해싱하기 위한 BCryptPasswordEncoder를 빈으로 등록한다.
        // BCrypt는 강력한 단방향 해싱 알고리즘으로 비밀번호를 저장할 때 사용된다.
        return new BCryptPasswordEncoder();
    }

    @Bean // 이 메서드가 스프링 빈을 생성함을 나타낸다.
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // 스프링 시큐리티의 인증 관리자(AuthenticationManager)를 빈으로 등록한다.
        // AuthenticationManager는 사용자 인증을 처리하는 핵심 인터페이스이다.
        // AuthenticationConfiguration을 통해 기본 인증 관리자를 가져온다.
        return authenticationConfiguration.getAuthenticationManager();
    }
}
