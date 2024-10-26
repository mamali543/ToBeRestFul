package com.ader.RestApi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.ader.RestApi.config.TestConfig;

@SpringBootTest
@Import(TestConfig.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
class RestApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
