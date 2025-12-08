package com.church.guest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableFeignClients
@EnableMongoAuditing
@SpringBootApplication
public class GuestApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuestApplication.class, args);
	}

}
