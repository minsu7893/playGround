package com.playGround.playGround;


import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("com.playGround.mapper")
class PlayGroundApplicationTests {

	@Test
	void contextLoads() {
	}

}
