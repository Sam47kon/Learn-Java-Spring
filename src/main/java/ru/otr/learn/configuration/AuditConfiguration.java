package ru.otr.learn.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Optional;

@EnableJpaAuditing(auditorAwareRef = "auditAware", modifyOnCreate = false)
@Configuration
public class AuditConfiguration {

	@Bean
	public AuditorAware<String> auditAware() {
		return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
				//.map(authentication -> (UserDetails) authentication.getPrincipal())
				//.map(UserDetails::getUsername);
				.map(Principal::getName);
	}
}
