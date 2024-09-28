package com.playGround.playGround.Controller;

import com.playGround.playGround.Service.comm;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://minsuportfolio.xyz")  // 또는 모든 도메인을 허용하려면 "*"
public class playGroundController {

    @PostMapping("/start-crawling")
    public String startCrawling() {
        System.out.println("시작 완료");
        try {
            // 실제 자바 프로젝트의 실행 로직 (예: 크롤러 실행)
            comm comm = new comm();
            int result = comm.sendTelegram("test");

            if (result != 200) {
                throw new Exception("");
            }
            return "크롤링이 성공적으로 시작되었습니다.";
        } catch (Exception e) {
            return "크롤링 시작 중 오류가 발생했습니다.";
        }
    }
}
