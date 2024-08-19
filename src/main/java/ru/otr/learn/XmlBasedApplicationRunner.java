package ru.otr.learn;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otr.learn.service.xmlbased.CompanyService;
import ru.otr.learn.service.xmlbased.UserService;

import static ru.otr.learn.utils.Utils.prettyList;

public class XmlBasedApplicationRunner {

	public static void main(String[] args) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("xml-based.xml");

		UserService userService = context.getBean(UserService.class);
		System.out.println("userService.getAllUsers():\n\t" + prettyList(userService.getAllUsers()) + System.lineSeparator());

		CompanyService companyService = context.getBean(CompanyService.class);
		System.out.println("companyService.getAllCompanies():\n\t" + prettyList(companyService.getAllCompanies()) + System.lineSeparator());

		System.out.println("Closing application context");
		context.close();
		System.out.println("Application context closed");
	}
}
