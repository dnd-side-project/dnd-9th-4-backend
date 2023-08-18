package com.dnd.health;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class HealthApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthApplication.class, args);
	}

}
