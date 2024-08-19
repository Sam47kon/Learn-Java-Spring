package ru.otr.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otr.learn.configuration.DbProperties;
import ru.otr.learn.service.CompanyService;
import ru.otr.learn.service.UserService;

import static ru.otr.learn.utils.Utils.prettyList;

@SpringBootApplication
//@ConfigurationPropertiesScan
public class ApplicationRunner {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ApplicationRunner.class, args);

		UserService userService = context.getBean(UserService.class);
		System.out.println("userService.getAllUsers():\n\t" + prettyList(userService.getAllUsers()) + System.lineSeparator());

		CompanyService companyService = context.getBean(CompanyService.class);
		System.out.println("companyService.getAllCompanies():\n\t" + prettyList(companyService.getAllCompanies()) + System.lineSeparator());

		System.out.println("companyService.getCompanyByName(\"ОТР\") = " + companyService.getCompanyByName("ОТР") + System.lineSeparator());

		DbProperties dbProperties = context.getBean(DbProperties.class);
		System.out.println("dbProperties = " + dbProperties);

		System.out.println("Closing application context");
		context.close();
		System.out.println("Application context closed");
	}
}
