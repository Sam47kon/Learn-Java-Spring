package ru.otr.learn.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing(auditorAwareRef = "auditAware", modifyOnCreate = false)
@Configuration
public class AuditConfiguration {

	@Bean
	public AuditorAware<String> auditAware() {
		// TODO переписать потом на Spring Security
		// return SecurityContextHolder.getContext().getAuthentication().getName();
		// return SecurityContext.getCurrentUser().getUsername();
		return () -> Optional.of("admin");
	}
}
