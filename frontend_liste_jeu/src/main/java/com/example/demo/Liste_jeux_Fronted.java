package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication
@EnableFeignClients
public class Liste_jeux_Fronted {

	public static void main(String[] args) {
		SpringApplication.run(Liste_jeux_Fronted.class, args);
	}

}
