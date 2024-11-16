package com.playGround.Controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playGround.Service.KtxService;
import com.playGround.model.KtxRequest;

import io.micrometer.common.util.StringUtils;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://minsuportfolio.xyz")  // 또는 모든 도메인을 허용하려면 "*"
public class KtxController {
	
	private final KtxService ktxService;
	
	@Autowired
	public KtxController(KtxService ktxservice) {
		this.ktxService = ktxservice;
	}
	
	@PostMapping("/start-ktx")
	public ResponseEntity<KtxRequest> startKtx(@RequestBody KtxRequest ktxRequest) {
		
		System.out.println("KtxController.startKtx Start");
		System.out.println(ktxRequest);

		if(StringUtils.isBlank(ktxRequest.getStart())) {
			ktxRequest.setPrcsRslt("출발역이 공란입니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(ktxRequest);
		}
		
		if(StringUtils.isBlank(ktxRequest.getEnd())) {
			ktxRequest.setPrcsRslt("도착역이 공란입니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(ktxRequest);
		}
		
		if(ktxRequest.getYear() == 0) {
			ktxRequest.setPrcsRslt("출발 날짜가 공란입니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(ktxRequest);
		}
		
		if(ktxRequest.getMonth() == 0) {
			ktxRequest.setPrcsRslt("출발 날짜가 공란입니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ktxRequest);
		}
		
		if(ktxRequest.getDay() == 0) {
			ktxRequest.setPrcsRslt("출발 날짜가 공란입니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ktxRequest);
		}
		
		if(StringUtils.isBlank(ktxRequest.getDayOfWeek())) {
			ktxRequest.setPrcsRslt("출발 날짜가 공란입니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ktxRequest);
		}
		
		if(StringUtils.isBlank(ktxRequest.getTime())) {
			ktxRequest.setPrcsRslt("출발 시간이 공란입니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ktxRequest);
		}
		
		if(ktxRequest.getStart().equals(ktxRequest.getEnd())) {
			ktxRequest.setPrcsRslt("출발역과 도착역이 같습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ktxRequest);
		}
		
		LocalDate input = LocalDate.of(ktxRequest.getYear(), ktxRequest.getMonth(), ktxRequest.getDay());
		
		
		if(input.isBefore(LocalDate.now())) {
			ktxRequest.setPrcsRslt("출발 날짜가 과거입니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ktxRequest);
		}
		
		
		ktxRequest = ktxService.ktxStart(ktxRequest);
		
		System.out.println("KtxController.startKtx End");
		System.out.println(ktxRequest);
		
		if(StringUtils.isBlank(ktxRequest.getPrcsRslt())) {
			return ResponseEntity.ok(ktxRequest);	
		}
		else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ktxRequest);
		}

	}
	
	@PostMapping("/stop-ktx")
	public ResponseEntity<String> startKtx() {
		
        boolean stopped = ktxService.stopKtx();
        
        if (stopped) {
            return ResponseEntity.ok("작업이 중지되었습니다.");
        } else {
            return ResponseEntity.status(404).body("진행 중인 작업이 없습니다.");
        }
	}

}
