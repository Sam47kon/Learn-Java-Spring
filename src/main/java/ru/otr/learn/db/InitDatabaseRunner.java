package ru.otr.learn.db;

import jakarta.annotation.PostConstruct;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.otr.learn.entity.Company;
import ru.otr.learn.entity.User;
import ru.otr.learn.entity.User.Role;
import ru.otr.learn.service.CompanyService;
import ru.otr.learn.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Value
@NonFinal
@Component
public class InitDatabaseRunner {
	private static final Random RANDOM = new Random();
	UserService userService;
	CompanyService companyService;

	@PostConstruct
	public void init() {
		List<User> users = initUsers();
		initCompanies(users);
	}

	private @NotNull List<User> initUsers() {
		List<User> users = new ArrayList<>();
		createUser(users, "Илья", 28, Role.DEV);
		createUser(users, "Никита", 29, Role.MAN);
		createUser(users, "Алина", 30, Role.QA);
		createUser(users, "Игорь", 47, Role.DEV);
		createUser(users, "Антон", 48, Role.MAN);
		createUser(users, "Елена", 50, Role.QA);
		createUser(users, "Николай", 31, Role.DEV);
		createUser(users, "Александр", 32, Role.MAN);
		createUser(users, "Анатолий", 33, Role.QA);
		createUser(users, "Ирина", 21, Role.DEV);
		createUser(users, "Григорий", 22, Role.MAN);
		createUser(users, "Сергей", 23, Role.QA);

		log.info("Таблица USER наполнена");
		return users;
	}

	private void createUser(@NotNull List<User> users, String name, int age, Role role) {
		User user = User.builder()
				.name(name)
				.age(age)
				.role(role)
				.build();
		user = userService.createUser(user);
		log.info("Создан user {}", user);
		users.add(user);
	}

	private void initCompanies(List<User> users) {
		createCompany("ОТР", users, 0, 3);
		createCompany("Гугл", users, 3, 6);
		createCompany("Яндекс", users, 6, 9);
		createCompany("Тинькофф", users, 9, 12);

		log.info("[DATABASE]: Таблица COMPANIES инициализирована");
	}

	private void createCompany(String name, List<User> users, int from, int to) {
		Company company = Company.builder()
				.name(name)
				.users(getUsers(users, from, to))
				.build();
		Company createdCompany = companyService.createCompany(company);
		log.info("Создана company {}", createdCompany);
	}

	@Contract("_, _, _ -> new")
	private @NotNull List<User> getUsers(@NotNull List<User> users, int from, int to) {
		return new ArrayList<>(users.subList(from, to));
	}
}
