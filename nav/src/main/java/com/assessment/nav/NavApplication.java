package com.assessment.nav;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NavApplication {
	private static Logger LOG = LoggerFactory.getLogger(NavApplication.class);
	
	public static void main(String[] args) {
		LOG.info("STARTING APPLICATION");
		SpringApplication.run(NavApplication.class, args);
		LOG.info("APPLICATION FINISHED");
	}

}
