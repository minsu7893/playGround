package com.playGround.Controller;

import com.playGround.Security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
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
