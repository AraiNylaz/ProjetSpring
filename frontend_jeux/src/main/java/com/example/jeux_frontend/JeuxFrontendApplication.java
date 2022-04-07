package com.example.jeux_frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class JeuxFrontendApplication {

	public static void main(String[] args) {
		SpringApplication.run(JeuxFrontendApplication.class, args);
	}

}
