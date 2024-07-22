package com.victor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class BackendAppStarter extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BackendAppStarter.class);
		// ngrok http --domain=mint-mammal-secretly.ngrok-free.app 8080
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendAppStarter.class, args);
	}
}
