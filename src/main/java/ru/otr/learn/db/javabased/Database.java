package ru.otr.learn.db.javabased;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otr.learn.bpp.Transaction;
import ru.otr.learn.entity.Company;
import ru.otr.learn.entity.User;
import ru.otr.learn.entity.javabased.SystemUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Getter
public class Database {

	private static final Random RANDOM = new Random();
	private static final String USER_SEQUENCE = "user_sequence";
	private static final String COMPANY_SEQUENCE = "company_sequence";


	private final IdGenerator idGenerator;
	private final SystemUser systemUser;

	private final List<User> USERS = new ArrayList<>();
	private final List<Company> COMPANIES = new ArrayList<>();

	private boolean usersInitialized = false;
	private boolean companiesInitialized = false;

	public Database(@Autowired IdGenerator idGenerator, @Autowired SystemUser systemUser) {
		this.idGenerator = idGenerator;
		this.systemUser = systemUser;
	}

	@PostConstruct
	private void init() {
		System.out.println("[DATABASE]: Соединение с БД открыто");
		initUsers();
		initCompanies();
	}

	@PreDestroy
	private void destroy() {
		System.out.println("[DATABASE]: Соединение с БД закрыто");
	}

	public User createUser(@NotNull User user) {
		user.setId(generateUserId());
		USERS.add(user);
		return user;
	}

	public Company createCompany(@NotNull Company company) {
		company.setId(generateCompanyId());
		COMPANIES.add(company);
		return company;
	}


	@Transaction
	private void initUsers() {
		if (usersInitialized) {
			System.out.println("[DATABASE]: Таблица USERS уже была инициализирована ранее");
			return;
		}

		createUser(systemUser);
		createUser(new User("Илья", 28));
		createUser(new User("Антон", getRandomAge()));
		createUser(new User("Игорь", getRandomAge()));
		createUser(new User("Елена", getRandomAge()));
		createUser(new User("Анатолий", getRandomAge()));
		createUser(new User("Николай", getRandomAge()));

		usersInitialized = true;
		System.out.println("[DATABASE]: Таблица USERS инициализирована");
	}

	private void initCompanies() {
		if (companiesInitialized) {
			System.out.println("[DATABASE]: Таблица COMPANIES уже была инициализирована ранее");
			return;
		}

		createCompany(new Company("ОТР", getRandomUsers()));
		createCompany(new Company("Автошкола", getRandomUsers()));
		createCompany(new Company("Компания 2", getRandomUsers()));
		createCompany(new Company("Компания 3", getRandomUsers()));
		createCompany(new Company("Компания 4", getRandomUsers()));

		companiesInitialized = true;
		System.out.println("[DATABASE]: Таблица COMPANIES инициализирована");
	}

	private int getRandomInt(int min, int max) {
		return RANDOM.nextInt(max - min + 1) + min;
	}

	private int getRandomAge() {
		return getRandomInt(20, 60);
	}

	private @NotNull List<User> getRandomUsers() {
		if (USERS.isEmpty()) {
			usersInitialized = false;
			initUsers();
		}
		return IntStream.range(0, getRandomInt(1, 3))
				.mapToObj(i -> getRandomUser())
				.collect(Collectors.toList());
	}

	private User getRandomUser() {
		return USERS.get(getRandomInt(0, USERS.size() - 1));
	}

	private long generateUserId() {
		return idGenerator.nextId(USER_SEQUENCE);
	}

	private long generateCompanyId() {
		return idGenerator.nextId(COMPANY_SEQUENCE);
	}
}
