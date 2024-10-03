package com.playGround.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playGround.Service.CommService;
import com.playGround.mapper.UsersMapper;
import com.playGround.model.Users;
import com.playGround.model.UsersExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.util.ArrayList;
import java.util.List;


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
        successHandler.setDefaultTargetUrl("/playGround/");
        successHandler.setAlwaysUseDefaultTargetUrl(true);  // 무조건 해당 URL로 리다이렉트

        http
                .requiresChannel(channel -> channel
                        .anyRequest().requiresSecure()  // 모든 요청을 HTTPS로 강제
                )
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/index.html").authenticated()  // index.html에만 인증 요구
                        .anyRequest().permitAll()  // 나머지 요청은 모두 허용
                )
                .formLogin((form) -> form
                        .loginPage("/login.html")  // 커스텀 로그인 페이지 경로
                        .loginProcessingUrl("/login")  // 로그인 처리 경로
                        .successHandler(successHandler)  // 로그인 성공 시 핸들러 설정
                        .permitAll()  // 로그인 페이지는 누구나 접근 가능
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login.html")  // 로그아웃 성공 시 login.html로 리다이렉트
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());  // 테스트 목적으로 CSRF 비활성화

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

        String pcsnYN = "";

        List<Users> users = null;
        try {
            Users usersModel = new Users();

            usersModel.setUsername(userName);  //유저 ID
            usersModel.setPassword(passWord);  //비밀번호
            usersModel.setEnabled("Y");        //기본 사용자 Y

            int pcsnNbi = usersMapper.insert(usersModel);

            if(pcsnNbi != 1){
                throw new Exception();
            }else {
                pcsnYN = "Y";

                commService.sendTelegram("신규회원 : " + userName);

            }

        } catch (Exception e) {
            return "N";
        }

        return pcsnYN;
    }
}
