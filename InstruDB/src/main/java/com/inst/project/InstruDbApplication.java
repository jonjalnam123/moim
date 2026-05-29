package com.inst.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InstruDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstruDbApplication.class, args);
	}

}
