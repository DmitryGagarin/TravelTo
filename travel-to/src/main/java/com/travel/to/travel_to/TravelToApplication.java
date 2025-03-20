package com.travel.to.travel_to;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TravelToApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelToApplication.class, args);
	}

}
