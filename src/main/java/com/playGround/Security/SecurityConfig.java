package com.playGround.Security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playGround.Service.CommService;
import com.playGround.mapper.UsersMapper;
import com.playGround.model.Users;
import com.playGround.model.UsersExample;

import jakarta.servlet.http.HttpServletRequest;


@Configuration
public class SecurityConfig {

    private final UsersMapper usersMapper;
    private final CommService commService;

    @Autowired
    public SecurityConfig(UsersMapper usersMapper, CommService commService) {
        this.usersMapper = usersMapper;
        this.commService = commService;
    }

    // 메모리 내 사용자 정보를 설정하는 UserDetailsService 빈 정의
    @Bean
    public UserDetailsService userDetailsService() {

        String username = "";
        String password = "";
        // UserDetails 리스트 생성
        List<UserDetails> userDetailsList = new ArrayList<>();

        List<Users> users = this.findUser();

        for(int i = 0 ; i < users.size() ; i++){

            username = users.get(i).getUsername();
            password = users.get(i).getPassword();

            UserDetails user = User.withUsername(username)
                    .password("{noop}" + password)  // 비밀번호는 암호화되지 않음 (noop 사용)
                    .roles("USER")  // 모든 사용자에게 "USER" 역할 부여
                    .build();

            userDetailsList.add(user);
        }

        // 메모리 내 사용자 관리
        return new InMemoryUserDetailsManager(userDetailsList);
    }

    // SecurityFilterChain 빈 정의 (보안 설정)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 로그인 성공 시 무조건 /playGround/로 리다이렉트하는 SuccessHandler
        SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
        successHandler.setDefaultTargetUrl("https://minsuportfolio.xyz/playGround/");
        successHandler.setAlwaysUseDefaultTargetUrl(true);  // 항상 이 URL로 리다이렉트

        http
                .authorizeHttpRequests((requests) -> requests
                        // 로컬 IP로 접속 시 모든 요청 허용
                        .requestMatchers(new RequestMatcher() {
                            @Override
                            public boolean matches(HttpServletRequest request) {
                                String remoteAddr = request.getRemoteAddr();
                                return "127.0.0.1".equals(remoteAddr) || "0:0:0:0:0:0:0:1".equals(remoteAddr); // IPv6 로컬 주소
                            }
                        }).permitAll()
                        .requestMatchers("/playGround/**").authenticated()  // playGround 경로는 인증된 사용자만 접근 가능
                        .requestMatchers("/index.html").authenticated()  // index.html에도 인증 요구
                        .anyRequest().permitAll()  // 나머지 요청은 모두 허용
                )
                .formLogin((form) -> form
                        .loginPage("/login.html")  // 커스텀 로그인 페이지 경로
                        .loginProcessingUrl("/login")  // 로그인 처리 경로
                        .successHandler(successHandler)  // 성공 시 이전 경로로 리다이렉트, 없으면 기본 경로
                        .permitAll()  // 로그인 페이지는 누구나 접근 가능
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login.html")  // 로그아웃 성공 시 login.html로 리다이렉트
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    public List<Users> findUser() {

        List<Users> users = null;
        try {

            UsersExample usersModel = new UsersExample();

            usersModel.createCriteria().andEnabledEqualTo("Y");

            ObjectMapper mapper = new ObjectMapper();

            users = usersMapper.selectByExample(usersModel);

        } catch (Exception e) {
            System.out.println();
        }
        return users;
    }


    /**
     * @param userName
     * @param passWord
     * @return 처리상태 여부 (Y : 정상, N : 오류)
     */
    public String registerUser(String userName, String passWord){

        System.out.println("=========================");
        System.out.println("USERNAME : " + userName);
        System.out.println("PASSWORD : " + passWord);
        System.out.println("=========================");

        // userName이 영문자와 숫자로만 이루어지지 않은 경우에 오류 메시지 반환
        if (userName == null || !userName.matches("^[a-zA-Z0-9]+$")) {
            return "ID는 영문과 숫자만 입력가능합니다.";
        }

        // passWord가 영문자, 숫자, 특수문자 외에 다른 문자가 있는 경우에 오류 메시지 반환
        if (passWord == null || !passWord.matches("^[a-zA-Z0-9!@#$%^&*()-_+=<>?]+$")) {
            return "PW는 영문, 숫자, 특수문자만 입력가능합니다.";
        }

        String pcsnYN = "";

        List<Users> users = null;
        try {
            Users usersModel = new Users();

            usersModel.setUsername(userName);  //유저 ID
            usersModel.setPassword(passWord);  //비밀번호
            usersModel.setEnabled("Y");        //기본 사용자 Y

            int pcsnNbi = usersMapper.insert(usersModel);

            if (pcsnNbi != 1) {
                throw new Exception();
            } else {
                commService.sendTelegram("신규회원 : " + userName);
            }
        }catch (DuplicateKeyException e) {
            return "이미 가입된 ID입니다.";
        } catch (Exception e) {
            return "오류 !";
        }
        return "";
    }
}
