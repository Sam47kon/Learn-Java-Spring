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
import ru.otr.learn.repository.impl.UserRepository;
import ru.otr.learn.service.CompanyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ru.otr.learn.utils.Utils.getRandomDate;

@Slf4j
@Value
@NonFinal
@Component
public class InitDatabaseRunner {
	private static final Random RANDOM = new Random();
	UserRepository userRepository;
	CompanyService companyService;

	@PostConstruct
	public void init() {
		List<User> users = initUsers();
		initCompanies(users);
	}

	private @NotNull List<User> initUsers() {
		List<User> users = new ArrayList<>();
		createUser(users, "sam47kon", "Илья", 28, Role.ADMIN);
		createUser(users, "nikita", "Никита", 29, Role.MAN);
		createUser(users, "alina", "Алина", 30, Role.QA);
		createUser(users, "igor", "Игорь", 47, Role.DEV);
		createUser(users, "anton", "Антон", 48, Role.MAN);
		createUser(users, "elena", "Елена", 50, Role.QA);
		createUser(users, "nikolay", "Николай", 31, Role.DEV);
		createUser(users, "alexandr", "Александр", 32, Role.MAN);
		createUser(users, "anatoly", "Анатолий", 33, Role.QA);
		createUser(users, "irina", "Ирина", 21, Role.DEV);
		createUser(users, "grigory", "Григорий", 22, Role.MAN);
		createUser(users, "sergey", "Сергей", 23, Role.QA);

		log.info("Таблица USER наполнена");
		return users;
	}

	private void createUser(@NotNull List<User> users, String login, String name, int age, Role role) {
		User user = User.builder()
				.username(login)
				.name(name)
				.age(age)
				.birthDate(getRandomDate())
				.role(role)
				.build();
		user = userRepository.saveAndFlush(user);
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
		List<User> someUsers = getUsers(users, from, to);
		Company company = Company.builder()
				.name(name)
				.users(someUsers)
				.build();
		Company createdCompany = companyService.createCompany(company);
		someUsers.forEach(user -> {
			user.setCompany(createdCompany);
			userRepository.saveAndFlush(user);
		});
		log.info("Создана company {}", createdCompany);
	}

	@Contract("_, _, _ -> new")
	private @NotNull List<User> getUsers(@NotNull List<User> users, int from, int to) {
		return new ArrayList<>(users.subList(from, to));
	}
}
