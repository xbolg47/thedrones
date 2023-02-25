package com.musala.thedrone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.musala.thedrone", "com.musala.thedrone.data" })
public class ThedroneApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThedroneApplication.class, args);
	}

}
