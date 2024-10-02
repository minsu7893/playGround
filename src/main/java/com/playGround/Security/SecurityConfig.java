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
                        .requestMatchers("/index.html").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin((form) -> form
                        .loginPage("/login.html")  // 사용자 정의 로그인 페이지 경로
                        .loginProcessingUrl("/login")  // 로그인 처리 경로
                        .defaultSuccessUrl("/index.html", true)
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login.html")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());  // 테스트 목적으로 CSRF 비활성화

        return http.build();
    }

}