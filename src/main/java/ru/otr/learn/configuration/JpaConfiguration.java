package ru.otr.learn.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import ru.otr.learn.configuration.condition.JpaCondition;

@Conditional(JpaCondition.class)
@Configuration
public class JpaConfiguration {

	@Bean
	public DbProperties dbProperties() {
		System.out.println("create dbProperties");
		return new DbProperties();
	}

	@PostConstruct
	public void init() {
		System.out.println("JpaConfiguration init");
	}
}
