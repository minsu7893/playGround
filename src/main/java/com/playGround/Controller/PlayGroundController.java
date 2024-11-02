package com.playGround.Controller;

import com.playGround.Security.SecurityConfig;
import com.playGround.Service.CommService;
import com.playGround.Service.PlayGroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://minsuportfolio.xyz")  // 또는 모든 도메인을 허용하려면 "*"
public class PlayGroundController {

    private final CommService commService;
    private final SecurityConfig securityConfig;
    private final PlayGroundService playGroundService;  // playGroundService 필드 선언

    @Autowired
    public PlayGroundController(CommService commService, SecurityConfig securityConfig, PlayGroundService playGroundService) {
        this.commService = commService;
        this.securityConfig = securityConfig;
        this.playGroundService = playGroundService;  // playGroundService 주입
    }

    @PostMapping("/start-crawling")
    public String startCrawling() {
        try {
            int result = commService.sendTelegram("test");
            if (result != 200) {
                throw new Exception("Error in sending Telegram message");
            }
            return "크롤링이 성공적으로 시작되었습니다.";
        } catch (Exception e) {
            return "크롤링 시작 중 오류가 발생했습니다.";
        }
    }

    @PostMapping("/start-ssh")
    public String startSsh() {
        try {
            int result = playGroundService.ssh();
            if (result != 200) {
                throw new Exception("Error in starting SSH");
            }
            return "성공";
        } catch (Exception e) {
            return "실패";
        }
    }

    @PostMapping("/sqlTest")
    public void sqlTest() {
        commService.sqlTest();
    }

    @PostMapping("/chromeTest")
    public String chromeTest() {
        try {
            commService.chromTest();
        } catch (Exception e) {
            e.printStackTrace();
            return "에러";
        }
        return "정상";
    }

    @PostMapping("/findUsers")
    public String findUsers() {
        try {
            securityConfig.findUser();
        } catch (Exception e) {
            e.printStackTrace();
            return "에러";
        }
        return "정상";
    }
}
