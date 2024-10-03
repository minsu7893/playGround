package com.playGround.Controller;

import com.playGround.Security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://minsuportfolio.xyz")  // 특정 도메인 허용 또는 모든 도메인을 허용하려면 "*"
public class RegistrationController {

    private final SecurityConfig securityConfig;

    @Autowired
    public RegistrationController(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String password = payload.get("password");

        // 유저 등록 시도
        String pcsnYN = securityConfig.registerUser(username, password);

        if (pcsnYN == null || "N".equals(pcsnYN)) {
            // 에러 응답과 메시지 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("User registration failed due to an internal error.");
        }

        // 성공 응답과 메시지 반환
        return ResponseEntity.status(HttpStatus.OK)
                .body("회원가입이 정상처리됐습니다.");
    }
}