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
                        .loginPage("/login.html")  // 커스텀 로그인 페이지 경로
                        .defaultSuccessUrl("/index.html", true)
                        .permitAll()  // 로그인 페이지는 누구나 접근 가능
                )
                .logout(LogoutConfigurer::permitAll);  // 로그아웃 허용

        return http.build();
    }
}

