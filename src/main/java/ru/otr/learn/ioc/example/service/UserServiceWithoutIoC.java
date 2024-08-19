package ru.otr.learn.ioc.example.service;

import ru.otr.learn.ioc.example.UserRepository;

public class UserServiceWithoutIoC extends UserService {

	public UserServiceWithoutIoC() {
		// Создание зависимости внутри класса
		this.userRepository = new UserRepository();
	}
	// методы
}