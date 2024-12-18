package com.energytracker.userservice;

import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(locations="classpath:test-application.properties")
@ActiveProfiles("test")
class UserserviceApplicationTests {

	@MockBean
	private PasswordEncoder passwordEncoder;

	@MockBean(name = "userDetailsServiceImpl")
	private UserDetailsService userDetailsService;

	@Test
	void contextLoads() {
		assertNotNull(passwordEncoder);
		assertNotNull(userDetailsService);
	}

}
