package ru.otr.learn.listener;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.otr.learn.listener.entity.EntityEvent;

@Component
public class EntityListener {

	@EventListener(value = EntityEvent.class)
	@Order(2)
	public void accept(EntityEvent<?> entityEvent) {
		System.out.println("accept: " + entityEvent);
	}

	@EventListener(value = EntityEvent.class, condition = "#root.args[0].getOperationType().name() == 'READ'")
	@Order(1)
	public void acceptRead(EntityEvent<?> entityEvent) {
		System.out.println("acceptRead: " + entityEvent);
	}
}
