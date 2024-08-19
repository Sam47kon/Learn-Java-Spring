package ru.otr.learn;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otr.learn.service.annotationbased.CompanyService;
import ru.otr.learn.service.annotationbased.UserService;

import static ru.otr.learn.utils.Utils.prettyList;

public class AnnotationBasedApplicationRunner {

	public static void main(String[] args) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("annotation-based.xml");

		UserService userService = context.getBean("userService", UserService.class);
		System.out.println("[MAIN]: Результат userService.getAllUsers():\n\t" + prettyList(userService.getAllUsers()) + System.lineSeparator());

		CompanyService companyService = context.getBean(CompanyService.class);
		System.out.println("[MAIN]: companyService.getAllCompanies():\n\t" + prettyList(companyService.getAllCompanies()) + System.lineSeparator());

		System.out.println("[MAIN]: Closing application context");
		context.close();
		System.out.println("[MAIN]: Application context closed");
	}
}
