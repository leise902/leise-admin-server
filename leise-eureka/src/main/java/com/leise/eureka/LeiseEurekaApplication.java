package com.leise.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LeiseEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeiseEurekaApplication.class, args);
	}
}
