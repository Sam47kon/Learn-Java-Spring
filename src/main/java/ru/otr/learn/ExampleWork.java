package ru.otr.learn;

import lombok.SneakyThrows;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otr.learn.configuration.DbProperties;
import ru.otr.learn.service.CompanyService;
import ru.otr.learn.service.UserService;

import static ru.otr.learn.utils.Utils.prettyList;

@Slf4j
@Value
@NonFinal
@Component
public class ExampleWork implements ApplicationContextAware {

	UserService userService;
	CompanyService companyService;

	@NonFinal
	ApplicationContext context;

	public void example_1() {
		DbProperties dbProperties = context.getBean(DbProperties.class);
		log.debug("dbProperties = {}", dbProperties);
	}

	@Transactional
	public void example_2() {
		//UserService userService = context.getBean(UserService.class);
		log.debug("userService.getAllUsers():\n\t{}{}", prettyList(userService.getAllUsers()), System.lineSeparator());
	}

	@Transactional
	public void example_3() {
		//CompanyService companyService = context.getBean(CompanyService.class);
		log.debug("companyService.getAllCompanies():\n\t{}{}", prettyList(companyService.getAllCompanies()), System.lineSeparator());

		log.debug("companyService.getCompanyByName(\"ОТР\") = {}{}", companyService.getCompanyByName("ОТР"), System.lineSeparator());
	}

	@Transactional(timeout = 2)
	@SneakyThrows
	public void example_4() {
		log.debug("Ожидаем...");
		log.info("userService.getUserByName(\"Илья\") = {}", userService.getUserByName("Илья"));
		Thread.sleep(5_000);
		log.info("userService.getUserByName(\"Антон\") = {}", userService.getUserByName("Антон"));
		log.debug("Подождали 5 секунд");
	}

	@Override
	public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}
