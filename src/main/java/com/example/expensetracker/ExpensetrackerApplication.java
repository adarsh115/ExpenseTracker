package com.example.expensetracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExpensetrackerApplication {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(ExpensetrackerApplication.class);
		logger.info("🚀 App starting now...");


		SpringApplication.run(ExpensetrackerApplication.class, args);
	}

}
