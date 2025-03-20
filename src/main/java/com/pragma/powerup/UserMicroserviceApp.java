package com.pragma.powerup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UserMicroserviceApp {

	public static void main(String[] args) {
		SpringApplication.run(UserMicroserviceApp.class, args);
	}

}
