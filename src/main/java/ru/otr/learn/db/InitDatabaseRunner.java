package ru.otr.learn.db;

import jakarta.annotation.PostConstruct;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.otr.learn.entity.Company;
import ru.otr.learn.entity.User;
import ru.otr.learn.service.CompanyService;
import ru.otr.learn.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Value
@NonFinal
@Component
public class InitDatabaseRunner {
	private static final Random RANDOM = new Random();
	UserService userService;
	CompanyService companyService;
	//CommandLineRunner runner;

	@PostConstruct
	public void init() {
		List<User> users = initUsers();
		initCompanies(users);
	}

	private @NotNull List<User> initUsers() {
		List<User> users = new ArrayList<>();
		createUser(users, "Илья", 28);
		createUser(users, "Антон", getRandomAge());
		createUser(users, "Игорь", getRandomAge());
		createUser(users, "Елена", getRandomAge());
		createUser(users, "Анатолий", getRandomAge());
		createUser(users, "Николай", getRandomAge());

		log.info("Таблица USER наполнена");
		return users;
	}

	private int getRandomAge() {
		return getRandomInt(20, 60);
	}

	private void createUser(@NotNull List<User> users, String name, int age) {
		User.UserBuilder userBuilder = User.builder()
				.name(name)
				.age(age)
				.role(User.Role.getRandom());

		User user = userBuilder.build();
		user = userService.createUser(user);
		log.info("Создан user {}", user);
		users.add(user);
	}

	private void initCompanies(List<User> users) {
		createCompany(Company.builder().name("ОТР").users(getRandomUsers(users)).build());
		createCompany(Company.builder().name("Автошкола").users(getRandomUsers(users)).build());
		createCompany(Company.builder().name("Гугл").users(getRandomUsers(users)).build());
		createCompany(Company.builder().name("ООО Чайка").users(getRandomUsers(users)).build());
		createCompany(Company.builder().name("Энергетика").users(getRandomUsers(users)).build());

		log.info("[DATABASE]: Таблица COMPANIES инициализирована");
	}

	private void createCompany(Company company) {
		Company createdCompany = companyService.createCompany(company);
		log.info("Создана company {}", createdCompany);
	}

	private List<User> getRandomUsers(List<User> users) {
		return IntStream.range(0, getRandomInt(1, 3))
				.mapToObj(i -> getRandomUser(users))
				.collect(Collectors.toList());
	}

	private User getRandomUser(@NotNull List<User> users) {
		return users.get(getRandomInt(0, users.size() - 1));
	}

	private int getRandomInt(int min, int max) {
		return RANDOM.nextInt(max - min + 1) + min;
	}
}
