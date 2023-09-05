package com.edhm.mascota.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MascotaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MascotaApiApplication.class, args);
	}

}
