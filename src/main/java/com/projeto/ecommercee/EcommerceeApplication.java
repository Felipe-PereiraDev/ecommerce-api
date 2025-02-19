package com.projeto.ecommercee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EcommerceeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceeApplication.class, args);
	}

}
