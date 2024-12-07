package com.energytracker.datacollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.energytracker")
@EnableScheduling
public class DatacollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatacollectorApplication.class, args);
	}

}
