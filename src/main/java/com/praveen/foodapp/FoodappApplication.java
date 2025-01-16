package com.praveen.foodapp;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodappApplication {

	static Logger logger = LogManager.getLogger(FoodappApplication.class);
	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.setProperty("log4j.append.file.File", "src/main/resources/log4j.properties");
		SpringApplication.run(FoodappApplication.class, args);
		logger.info("\n\n\n \t\t\t  <<<<=================----Application started successfully---===========>>>>  \n\n");
	}

}
