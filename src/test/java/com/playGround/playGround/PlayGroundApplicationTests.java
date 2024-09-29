package com.playGround.playGround;

import com.playGround.mapper.LandMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@Disabled
class PlayGroundApplicationTests {
	@MockBean
	private LandMapper landMapper;

	@Test
	void contextLoads() {
	}

}
