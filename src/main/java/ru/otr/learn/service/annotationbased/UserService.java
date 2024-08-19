package ru.otr.learn.service.annotationbased;

import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.otr.learn.bpp.Performance;
import ru.otr.learn.bpp.Transaction;
import ru.otr.learn.entity.User;
import ru.otr.learn.repository.annotationbased.UserRepository;

import java.util.List;

public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostConstruct
	private void init() {
		System.out.println("Initializing UserService");
	}

	@Transaction
	@Performance
	public @NotNull List<User> getAllUsers() {
		System.out.println("Получение всех пользователей...");
		return userRepository.findAll();
	}

	public @Nullable User getUserByName(String userName) {
		return userRepository.findByName(userName);
	}
}
