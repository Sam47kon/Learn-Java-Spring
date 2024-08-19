package ru.otr.learn.ioc.example;

import ru.otr.learn.ioc.crud.entity.User;

import static ru.otr.learn.ioc.crud.db.Database.USERS;

public class UserRepository {

	public User getUserByName(String userName) {
		return USERS.get(userName);
	}
}
