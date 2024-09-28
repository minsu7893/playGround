package com.playGround.playGround.Service;


import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class comm {

    public int sendTelegram(String msg) throws Exception {

        int responseCode = 0;
        {
            // 자바 프로젝트의 실행 로직 예시 (시스템 명령 실행)
            String token = "7434834048:AAFkQxwLQ36rTdvmE4lDDMAssszQ2kzQgS0";
            String chatId = "6303810683";

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
                String urlParameters = "chat_id=" + chatId + "&text=" + msg;

                // OutputStream으로 데이터 전송
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = urlParameters.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                // 응답 상태 코드 확인
                StringBuilder content = new StringBuilder();
                responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    System.out.println("메시지가 성공적으로 전송되었습니다.");
                    System.out.println("메시지 내용: " + msg);

                    // 응답 내용 읽기
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                } else {
                    throw new Exception(content.toString());
                }

            } catch (Exception e) {
                throw new Exception(String.valueOf(responseCode));
            }
        }

        return responseCode;
    }
}