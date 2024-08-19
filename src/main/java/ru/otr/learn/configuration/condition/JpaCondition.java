package ru.otr.learn.configuration.condition;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Slf4j
public class JpaCondition implements Condition {

	@Override
	public boolean matches(@NotNull ConditionContext context, @NotNull AnnotatedTypeMetadata metadata) {
		try {
			context.getClassLoader().loadClass("org.h2.Driver");
			log.info("{} matches TRUE", this.getClass().getSimpleName());
			return true;
		} catch (ClassNotFoundException e) {
			log.warn("{} matches FALSE", this.getClass().getSimpleName());
			return false;
		}
	}
}
