package ru.otr.learn.configuration.condition;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class JpaCondition implements Condition {

	@Override
	public boolean matches(@NotNull ConditionContext context, @NotNull AnnotatedTypeMetadata metadata) {
		try {
			context.getClassLoader().loadClass("org.postgresql.Driver");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}
