package com.playGround.Controller;

import com.playGround.Service.CommService;
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

    @Autowired
    public PlayGroundController(CommService commService) {
        this.commService = commService;
    }

    @PostMapping("/start-crawling")
    public String startCrawling() {
        try {
            // 실제 자바 프로젝트의 실행 로직 (예: 크롤러 실행)
            int result = commService.sendTelegram("test");

            if (result != 200) {
                throw new Exception("");
            }
            return "크롤링이 성공적으로 시작되었습니다.";
        } catch (Exception e) {
            return "크롤링 시작 중 오류가 발생했습니다.";
        }
    }

    @PostMapping("/sqlTest")
    public void sqlTest(){
        commService.sqlTest();
    }

    @PostMapping("/chromeTest")
    public String chromeTest(){

        try{
            commService.chromTest();
        }catch (Exception e){
            return "에러";
        }
        return "정상";
    }
}
