package com.playGround.playGround.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://minsuportfolio.xyz")  // 또는 모든 도메인을 허용하려면 "*"
public class playGroundController {

@PostMapping("/start-crawling")
    public String startCrawling() {
        System.out.println("시작 완료");
        try {
            // 실제 자바 프로젝트의 실행 로직 (예: 크롤러 실행)
            this.runCrawlingProcess();
            return "크롤링이 성공적으로 시작되었습니다.";
        } catch (Exception e) {
            return "크롤링 시작 중 오류가 발생했습니다.";
        }
    }

    private void runCrawlingProcess() throws IOException, InterruptedException {
        // 자바 프로젝트의 실행 로직 예시 (시스템 명령 실행)

        String token = "7434834048:AAFkQxwLQ36rTdvmE4lDDMAssszQ2kzQgS0";
        String chatId = "6303810683";
        String message = "test";
        
        String urlString = "https://api.telegram.org/bot" + token + "/sendMessage";

        try {
            // URL 객체 생성
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            // HTTP 메서드를 POST로 설정
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true); // OutputStream을 사용해서 데이터를 보낼 수 있도록 설정

            // 전송할 파라미터 설정
            String urlParameters = "chat_id=" + chatId + "&text=" + message;

            // OutputStream으로 데이터 전송
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = urlParameters.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // 응답 상태 코드 확인
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("메시지가 성공적으로 전송되었습니다.");
                System.out.println("메시지 내용: " + message);

                // 응답 내용 읽기
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                System.out.println("응답: " + content.toString());
            } else {
                System.out.println("메시지 전송에 실패하였습니다. 응답 코드: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
