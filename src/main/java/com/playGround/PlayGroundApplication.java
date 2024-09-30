package com.playGround;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.playGround.mapper")
@SpringBootApplication
public class PlayGroundApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlayGroundApplication.class, args);
	}

}
