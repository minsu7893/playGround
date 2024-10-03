package com.playGround.Controller;

import com.playGround.Security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://minsuportfolio.xyz")  // 또는 모든 도메인을 허용하려면 "*"
public class RegistrationController {

    private final SecurityConfig securityConfig;

    @Autowired
    public RegistrationController(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    @PostMapping("/register")
    @ResponseBody
    public HttpStatus registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password) {


        String pcsnYN = securityConfig.registerUser(username, password);

        if (pcsnYN == null || "N".equals(pcsnYN)) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }


        return HttpStatus.OK;
    }


}
