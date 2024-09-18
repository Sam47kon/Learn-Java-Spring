package ru.otr.learn.service;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otr.learn.bpp.Performance;
import ru.otr.learn.dto.UserCreateEditDto;
import ru.otr.learn.dto.UserReadDto;
import ru.otr.learn.entity.User;
import ru.otr.learn.listener.entity.EntityEvent;
import ru.otr.learn.listener.entity.OperationType;
import ru.otr.learn.mapper.UserCreateEditMapper;
import ru.otr.learn.mapper.UserReadMapper;
import ru.otr.learn.repository.impl.UserRepository;

import java.util.List;
import java.util.Optional;

@Value
@RequiredArgsConstructor
@NonFinal
@Slf4j
@Service
@Transactional(readOnly = true)
public class UserService implements ApplicationEventPublisherAware {

	UserRepository userRepository;
	UserReadMapper userReadMapper;
	UserCreateEditMapper userCreateEditMapper;
	@NonFinal
	ApplicationEventPublisher applicationEventPublisher;

	@Performance
	public @NotNull List<UserReadDto> getAllUsers() {
		log.info("Получение всех пользователей...");
		List<User> users = userRepository.findAll();
		return users.stream()
				.map(userReadMapper::transform)
				.toList();
	}

	public Optional<UserReadDto> getUserById(Long id) {
		return userRepository.findById(id).map(userReadMapper::transform);
	}

	public Optional<UserReadDto> getUserByLogin(String login) {
		return userRepository.findByLogin(login).map(user -> {
			applicationEventPublisher.publishEvent(new EntityEvent<>(user, OperationType.READ));
			return userReadMapper.transform(user);
		});
	}

	public Optional<UserReadDto> getUserByName(String userName) {
		return userRepository.findFirstByName(userName).map(user -> {
			applicationEventPublisher.publishEvent(new EntityEvent<>(user, OperationType.READ));
			return userReadMapper.transform(user);
		});
	}

	@Transactional
	public UserReadDto createUser(UserCreateEditDto user) {
		return Optional.of(user)
				.map(userCreateEditMapper::transform)
				.map(entity -> {
					User createdUser = userRepository.saveAndFlush(entity);
					applicationEventPublisher.publishEvent(new EntityEvent<>(createdUser, OperationType.CREATE));
					return userReadMapper.transform(createdUser);
				})
				.orElseThrow();
	}

	@Transactional
	public Optional<UserReadDto> updateUser(Long id, UserCreateEditDto userDto) {
		return userRepository.findById(id)
				.map(user -> userCreateEditMapper.transform(userDto, user))
				.map(entity -> {
					User updatedUser = userRepository.saveAndFlush(entity);
					applicationEventPublisher.publishEvent(new EntityEvent<>(updatedUser, OperationType.UPDATE));
					return userReadMapper.transform(updatedUser);
				});
	}

	@Transactional
	public boolean deleteUserById(Long id) {
		return userRepository.findById(id)
				.map(user -> {
					userRepository.delete(user);
					applicationEventPublisher.publishEvent(new EntityEvent<>(user, OperationType.DELETE));
					return true;
				}).orElseGet(() -> {
					log.error("Пользователь с id {} не найден", id);
					return false;
				});
	}

	@Override
	public void setApplicationEventPublisher(@NotNull ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
}
