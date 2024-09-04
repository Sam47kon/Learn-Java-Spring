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
		executeNoThrows(work::example_7_1);
		executeNoThrows(work::example_7_2_NamedQuery);
		executeNoThrows(work::example_7_3_NativeQuery);
		executeNoThrows(work::example_7_4_SimpleJpaQuery);
		executeNoThrows(work::example_7_5_PartTreeJpaQuery);
		executeNoThrows(work::example_7_6_Update);
		executeNoThrows(work::example_7_7_DynamicSort);
		executeNoThrows(work::example_7_8_Page);
		executeNoThrows(work::example_7_9_Slice);
		executeNoThrows(work::example_7_10_EntityGraph);

		log.debug("Closing application context");
		context.close();
		log.debug("Application context closed");
	}
}
