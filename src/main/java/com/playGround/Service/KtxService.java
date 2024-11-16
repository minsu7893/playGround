package com.playGround.Service;

import org.openqa.selenium.WebDriver;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.playGround.Util.WebDriverUtil;
import com.playGround.model.KtxRequest;
import java.util.concurrent.CompletableFuture;

@Service
public class KtxService {

    private CompletableFuture<Void> currentTask;

    @Async
    public KtxRequest ktxStart(KtxRequest ktxRequest) {
        System.out.println("KtxService.ktxStart Start");
        System.out.println(ktxRequest);

        if (currentTask != null && !currentTask.isDone()) {
            ktxRequest.setPrcsRslt("진행중인 작업이 있습니다.");
            return ktxRequest;
        }

        currentTask = CompletableFuture.runAsync(() -> {
            WebDriver driver = WebDriverUtil.getChromeDriver();

            try {
                while (true) { // 테스트를 위해 5번 반복하도록 설정
                    driver.get("http://naver.com");
                    System.out.println("Page source loaded: " + driver.getPageSource());
                }
            } finally {
                driver.quit(); // WebDriver를 종료하여 리소스 해제
            }
        });

        System.out.println("KtxService.ktxStart End");
        System.out.println(ktxRequest);

        return ktxRequest;
    }

    public boolean stopKtx() {
        if (currentTask != null && !currentTask.isDone()) {
            currentTask.cancel(true); // 작업 취소
            return true;
        }
        return false; // 현재 작업이 없거나 이미 완료된 상태
    }
}
