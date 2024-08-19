package ru.otr.learn.service.xmlbased;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.otr.learn.entity.User;
import ru.otr.learn.repository.xmlbased.UserRepository;

import java.util.List;

public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public @NotNull List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public @Nullable User getUserByName(String userName) {
		return userRepository.findByName(userName);
	}
}
