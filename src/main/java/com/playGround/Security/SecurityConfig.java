package com.playGround.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/index.html").authenticated() // index.html에 인증 요구
                        .anyRequest().permitAll()  // 나머지 요청은 모두 허용
                )
                .formLogin((form) -> form
                        .defaultSuccessUrl("/index.html", true) // 로그인 성공 시 index.html로 리다이렉트
                )
                .logout(LogoutConfigurer::permitAll);  // 로그아웃 허용

        return http.build();
    }
}

