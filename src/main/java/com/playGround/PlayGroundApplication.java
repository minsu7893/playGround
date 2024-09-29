package com.playGround;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.playGround.mapper")  // Mapper 인터페이스가 위치한 패키지 지정
public class PlayGroundApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlayGroundApplication.class, args);
	}

}
