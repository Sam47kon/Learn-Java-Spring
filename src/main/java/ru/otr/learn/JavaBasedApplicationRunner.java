package ru.otr.learn;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otr.learn.configuration.ApplicationConfiguration;
import ru.otr.learn.service.CompanyService;
import ru.otr.learn.service.UserService;

import static ru.otr.learn.utils.Utils.prettyList;

public class JavaBasedApplicationRunner {

	public static void main(String[] args) {
		view();
	}

	private static void view() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(ApplicationConfiguration.class);
		context.getEnvironment().setActiveProfiles("prod");
		context.refresh();

		UserService userService = context.getBean(UserService.class);
		System.out.println("userService.getAllUsers():\n\t" + prettyList(userService.getAllUsers()) + System.lineSeparator());

		CompanyService companyService = context.getBean(CompanyService.class);
		System.out.println("companyService.getAllCompanies():\n\t" + prettyList(companyService.getAllCompanies()) + System.lineSeparator());

		System.out.println("Closing application context");
		context.close();
		System.out.println("Application context closed");
	}
}
