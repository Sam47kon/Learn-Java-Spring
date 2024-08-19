package ru.otr.learn.ioc.crud.db;

import com.google.common.collect.Lists;
import ru.otr.learn.ioc.crud.entity.Company;
import ru.otr.learn.ioc.crud.entity.User;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Database {
	public static final String DEFAULT_USER_NAME = "Илья";

	private static final User DEFAULT_USER = new User(DEFAULT_USER_NAME, 28);
	private static final User USER_1 = new User("Антон", 45);
	private static final User USER_2 = new User("Игорь", 30);
	private static final User USER_3 = new User("Елена", 25);

	private static final Company COMPANY_1 = new Company("ОТР", Lists.newArrayList(DEFAULT_USER, USER_1));
	private static final Company COMPANY_2 = new Company("Автошкола", Lists.newArrayList(USER_2, USER_3));

	public static final Map<String, User> USERS = Lists.newArrayList(DEFAULT_USER, USER_1, USER_2, USER_3)
			.stream().map(user -> new AbstractMap.SimpleEntry<>(user.name(), user))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b));

	public static final Map<String, Company> COMPANIES = Lists.newArrayList(COMPANY_1, COMPANY_2)
			.stream().map(company -> new AbstractMap.SimpleEntry<>(company.name(), company))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b));

	public static void initCompanies() {
		COMPANIES.put("Компания 2", new Company("Компания 2", Lists.newArrayList(new User("Анатолий", 50))));
		// TODO
	}
}
