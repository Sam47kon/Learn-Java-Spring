package ru.otr.learn.configuration.example;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import ru.otr.learn.configuration.example.condition.JpaCondition;

@Slf4j
@Conditional(JpaCondition.class)
@Configuration
public class JpaConfiguration {

	@PostConstruct
	public void init() {
		log.info("JpaConfiguration init");
	}
}
