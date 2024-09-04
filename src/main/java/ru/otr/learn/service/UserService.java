package ru.otr.learn.service;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import ru.otr.learn.bpp.Performance;
import ru.otr.learn.entity.User;
import ru.otr.learn.listener.entity.EntityEvent;
import ru.otr.learn.listener.entity.OperationType;
import ru.otr.learn.repository.impl.UserRepository;

import java.util.List;
import java.util.Optional;

@Value
@RequiredArgsConstructor
@NonFinal
@Slf4j
@Service
public class UserService implements ApplicationEventPublisherAware {

	UserRepository userRepository;
	@NonFinal
	ApplicationEventPublisher applicationEventPublisher;

	@Performance
	public @NotNull List<User> getAllUsers() {
		log.info("Получение всех пользователей...");
		return userRepository.findAll();
	}


	public Optional<User> getUserByName(String userName) {
		return userRepository.findFirstByName(userName).map(user -> {
			applicationEventPublisher.publishEvent(new EntityEvent<>(user, OperationType.READ));
			return user;
		});
	}

	public User createUser(User user) {
		/*if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
			throw new UnsupportedOperationException("Невозможно выполнить операцию записи в read-only транзакции.");
		}*/
		User createdUser = userRepository.saveAndFlush(user);
		applicationEventPublisher.publishEvent(new EntityEvent<>(createdUser, OperationType.CREATE));
		return createdUser;
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public @Nullable User deleteByName(String userName) {
		if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
			throw new UnsupportedOperationException("Невозможно выполнить операцию записи в read-only транзакции.");
		}
		return userRepository.findFirstByName(userName).map(user -> {
			userRepository.delete(user);
			applicationEventPublisher.publishEvent(new EntityEvent<>(user, OperationType.DELETE));
			return user;
		}).orElse(null);
	}

	@Override
	public void setApplicationEventPublisher(@NotNull ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
}
