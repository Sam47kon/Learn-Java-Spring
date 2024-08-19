package ru.otr.learn.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.otr.learn.listener.entity.EntityEvent;

@Slf4j
@Component
public class EntityListener {

	@EventListener(value = EntityEvent.class, condition = "#root.args[0].getOperationType().name() == 'READ'")
	@Order(1)
	public void acceptRead(EntityEvent<?> entityEvent) {
		log.info("acceptRead: {}", entityEvent);
	}

	@EventListener(value = EntityEvent.class, condition = "#root.args[0].getOperationType().name() == 'DELETE'")
	@Order(1)
	public void acceptDelete(EntityEvent<?> entityEvent) {
		log.info("acceptDelete: {}", entityEvent);
	}

	@EventListener(value = EntityEvent.class, condition = "#root.args[0].getOperationType().name() == 'CREATE'")
	@Order(1)
	public void acceptCreate(EntityEvent<?> entityEvent) {
		log.info("acceptCreate: {}", entityEvent);
	}
}
