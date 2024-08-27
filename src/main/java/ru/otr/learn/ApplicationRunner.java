package ru.otr.learn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;

import static ru.otr.learn.utils.Utils.executeNoThrows;

@Slf4j
@EntityScan
@SpringBootApplication
@ConfigurationPropertiesScan
public class ApplicationRunner {

	public static void main(String[] args) {
		log.debug("Starting application");

		ConfigurableApplicationContext context = SpringApplication.run(ApplicationRunner.class, args);
		log.debug("Application context created");

		ExampleWork work = context.getBean(ExampleWork.class);
		executeNoThrows(work::example_6_1);
		executeNoThrows(work::example_6_2);
		//executeNoThrows(work::example_6_3);
		//executeNoThrows(work::example_6_4);

		log.debug("Closing application context");
		context.close();
		log.debug("Application context closed");
	}
}
