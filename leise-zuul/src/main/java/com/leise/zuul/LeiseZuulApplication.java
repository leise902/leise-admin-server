package com.leise.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


@SpringCloudApplication
@EnableZuulProxy
public class LeiseZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeiseZuulApplication.class, args);
	}
}
