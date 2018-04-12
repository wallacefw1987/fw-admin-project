package com.fw.springboot.hystrixserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class HystrixServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HystrixServerApplication.class, args);
	}
}
