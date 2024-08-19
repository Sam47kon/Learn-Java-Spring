package ru.otr.learn.service.javabased;

import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otr.learn.bpp.Performance;
import ru.otr.learn.bpp.Transaction;
import ru.otr.learn.entity.User;
import ru.otr.learn.repository.javabased.UserRepository;

import java.util.List;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(@Autowired UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostConstruct
	private void init() {
		System.out.println("Initializing UserService");
	}

	@Performance
	@Transaction
	public @NotNull List<User> getAllUsers() {
		System.out.println("Получение всех пользователей...");
		return userRepository.findAll();
	}

	public @Nullable User getUserByName(String userName) {
		return userRepository.findByName(userName);
	}
}
