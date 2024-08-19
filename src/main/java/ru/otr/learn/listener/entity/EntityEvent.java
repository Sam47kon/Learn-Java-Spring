package ru.otr.learn.listener.entity;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import ru.otr.learn.entity.BaseEntity;

@Getter
public class EntityEvent<T extends BaseEntity> extends ApplicationEvent {
	private final OperationType operationType;

	public EntityEvent(T entity, OperationType operationType) {
		super(entity);
		this.operationType = operationType;
	}
}
