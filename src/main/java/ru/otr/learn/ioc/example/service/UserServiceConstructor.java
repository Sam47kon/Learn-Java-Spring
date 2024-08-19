package ru.otr.learn.ioc.example.service;

import ru.otr.learn.ioc.example.UserRepository;

public class UserServiceConstructor extends UserService {
	public static final String BEAN_NAME = "userServiceConstructor";

	// Зависимость внедряется через конструктор
	public UserServiceConstructor(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
}
