package ru.otr.learn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;


@Slf4j
@EntityScan
@SpringBootApplication
@ConfigurationPropertiesScan
public class ApplicationRunner {

	public static void main(String[] args) {
		log.debug("Starting application");

		ConfigurableApplicationContext context = SpringApplication.run(ApplicationRunner.class, args);
		log.debug("Application context created");

		/*
		log.debug("Closing application context");
		context.close();
		log.debug("Application context closed");
		*/
	}
}
