package ru.otr.learn.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import ru.otr.learn.listener.entity.EntityEvent;
import ru.otr.learn.listener.entity.OperationType;

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
		if (TransactionSynchronizationManager.isActualTransactionActive() && TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
			log.warn("acceptCreate: Тип операции {}, однако транзакция в режиме только чтения, {}", OperationType.CREATE, entityEvent);
			return;
		}
		log.info("acceptCreate: {}", entityEvent);
	}
}
