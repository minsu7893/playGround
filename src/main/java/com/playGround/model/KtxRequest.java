package com.playGround.model;

import lombok.Data;

@Data
public class KtxRequest {
	
    //INPUT
	private String start     ;  // 출발지
    private String end       ;  // 도착지
    private int    year      ;  // 출발 년도
    private int    month     ;  // 출발 월
    private int    day       ;  // 출발 일
    private String dayOfWeek ;  // 출발 요일
    private String time      ;  // 출발 시간
                               
    //OUTPUT                   
    private String prcsRslt;   //처리결과
}
