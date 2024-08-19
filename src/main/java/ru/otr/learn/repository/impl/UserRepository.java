package ru.otr.learn.repository.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.otr.learn.db.Database;
import ru.otr.learn.entity.User;
import ru.otr.learn.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements CrudRepository<User> {

	private final Database database;

	public UserRepository(@Autowired @Qualifier(value = "database") Database database) {
		this.database = database;
	}

	@Override
	public @NotNull List<User> findAll() {
		return new ArrayList<>(database.getUSERS());
	}

	@Override
	public Optional<User> findByName(String userName) {
		return database.getUSERS().stream().filter(user -> userName.equals(user.getName())).findFirst();
	}

	@Override
	public User create(User user) {
		return database.createUser(user);
	}

	@Override
	public void delete(User user) {
		database.getUSERS().remove(user);
	}
}
