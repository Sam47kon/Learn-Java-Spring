package ru.otr.learn.repository.annotationbased;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.otr.learn.bpp.InjectBean;
import ru.otr.learn.db.annotationbased.Database;
import ru.otr.learn.entity.User;
import ru.otr.learn.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements CrudRepository<User> {

	private final Database database;

	public UserRepository(Database database) {
		this.database = database;
	}

	@Override
	public @NotNull List<User> findAll() {
		return new ArrayList<>(database.getUSERS());
	}

	@Override
	public @Nullable User findByName(String userName) {
		return database.getUSERS().stream().filter(user -> userName.equals(user.getName())).findFirst().orElse(null);
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
