package com.energytracker.consumptionservice;

import jakarta.activation.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations="classpath:test-application.properties")
@ActiveProfiles("test")
class ConsumptionserviceApplicationTests {

	@MockBean
	private DataSource dataSource;

	@Test
	void contextLoads() {
	}

}
