package ru.otr.learn.ioc.example.service;

import ru.otr.learn.ioc.example.UserRepository;
import ru.otr.learn.ioc.crud.entity.User;

import static ru.otr.learn.ioc.crud.db.Database.DEFAULT_USER_NAME;

public abstract class UserService {
	protected UserRepository userRepository;

	// Методы
	public User getDefaultUser() {
		return userRepository.getUserByName(DEFAULT_USER_NAME);
	}

	public User getUserByName(String name) {
		return userRepository.getUserByName(name);
	}
}
