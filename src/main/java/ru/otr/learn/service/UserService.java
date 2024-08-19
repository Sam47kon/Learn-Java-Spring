package ru.otr.learn.service;

import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import ru.otr.learn.bpp.Performance;
import ru.otr.learn.entity.User;
import ru.otr.learn.listener.entity.EntityEvent;
import ru.otr.learn.listener.entity.OperationType;
import ru.otr.learn.repository.impl.UserRepository;

import java.util.List;
import java.util.Optional;

@Value
@NonFinal
@Slf4j
@Service
public class UserService implements ApplicationEventPublisherAware {

	UserRepository userRepository;
	@NonFinal
	ApplicationEventPublisher applicationEventPublisher;

	public UserService(@Autowired UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Performance
	public @NotNull List<User> getAllUsers() {
		log.info("Получение всех пользователей...");
		return userRepository.findAll();
	}


	public @Nullable Optional<User> getUserByName(String userName) {
		return userRepository.findByName(userName).map(user -> {
			applicationEventPublisher.publishEvent(new EntityEvent<>(user, OperationType.READ));
			return user;
		});
	}

	public User createUser(User user) {
		User createdUser = userRepository.saveAndFlush(user);
		applicationEventPublisher.publishEvent(new EntityEvent<>(createdUser, OperationType.CREATE));
		return createdUser;
	}

	public @Nullable User deleteByName(String userName) {
		return userRepository.findByName(userName).map(user -> {
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
