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
		ConfigurableApplicationContext context = SpringApplication.run(ApplicationRunner.class, args);

		ExampleWork work = context.getBean(ExampleWork.class);
		work.example_1();
		work.example_2();
		work.example_3();
		try {
			work.example_4();
		} catch (Exception e) {
			log.error("Ошибка: {}", e.getMessage());
		}

		log.debug("Closing application context");
		context.close();
		log.debug("Application context closed");
	}
}
