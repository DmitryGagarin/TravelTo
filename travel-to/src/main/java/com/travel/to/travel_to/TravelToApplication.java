package com.travel.to.travel_to;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = {
	org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration.class,
	org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration.class,
	org.springframework.boot.actuate.autoconfigure.metrics.export.influx.InfluxMetricsExportAutoConfiguration.class // Add this
})
@EnableDiscoveryClient
public class TravelToApplication {
	public static void main(String[] args) {
		SpringApplication.run(TravelToApplication.class, args);
	}
}
