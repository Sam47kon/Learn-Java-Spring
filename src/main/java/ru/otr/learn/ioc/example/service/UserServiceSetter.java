package ru.otr.learn.ioc.example.service;

import ru.otr.learn.ioc.example.UserRepository;

public class UserServiceSetter extends UserService {
	public static final String BEAN_NAME = "userServiceSetter";

	public UserServiceSetter() {
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
}
