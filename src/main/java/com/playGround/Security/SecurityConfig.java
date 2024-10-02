package com.playGround.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/index.html").authenticated()  // index.html에만 인증 요구
                        .anyRequest().permitAll()  // 나머지 요청은 모두 허용
                )
                .formLogin((form) -> form
                        .loginPage("/login.html")  // 커스텀 로그인 페이지 경로
                        .defaultSuccessUrl("/index.html", true)  // 로그인 성공 시 index.html로 리다이렉트
                        .permitAll()  // 로그인 페이지는 누구나 접근 가능
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login.html")  // 로그아웃 성공 시 login.html로 리다이렉트
                        .permitAll()
                );

        return http.build();  // 반드시 http.build()로 SecurityFilterChain 반환
    }
}