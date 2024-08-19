package ru.otr.learn.ioc.main;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otr.learn.ioc.crud.controller.CompanyController;
import ru.otr.learn.ioc.example.BeanExampleConstructor;
import ru.otr.learn.ioc.example.BeanExampleMix;
import ru.otr.learn.ioc.example.BeanExampleSetter;
import ru.otr.learn.ioc.example.UserRepository;
import ru.otr.learn.ioc.example.service.UserService;
import ru.otr.learn.ioc.example.service.UserServiceConstructor;
import ru.otr.learn.ioc.example.service.UserServiceSetter;

import static ru.otr.learn.ioc.crud.db.Database.DEFAULT_USER_NAME;

public class XmlApplicationRunner {

	public static void main(String[] args) {


		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

		example_1(context);
		example_2(context);
		example_3(context);
		example_4(context);

		System.out.println("Closing application context");
		context.close();
		System.out.println("Application context closed");
	}

	private static void example_1(@NotNull BeanFactory context) {
		System.out.println("START EXAMPLE_1:");

		BeanExampleConstructor beanExampleConstructor = context.getBean(BeanExampleConstructor.class);
		System.out.println(beanExampleConstructor + System.lineSeparator());

		BeanExampleSetter beanExampleSetter1 = context.getBean("beanExampleSetter_1", BeanExampleSetter.class);
		System.out.println(beanExampleSetter1);

		BeanExampleSetter beanExampleSetter2 = context.getBean("beanExampleSetter_2", BeanExampleSetter.class);
		System.out.println(beanExampleSetter2);

		System.out.println("END EXAMPLE_1" + System.lineSeparator());
	}

	private static void example_2(@NotNull BeanFactory context) {
		System.out.println("START EXAMPLE_2:");

		BeanExampleMix beanExampleMix = context.getBean("beanExampleMix", BeanExampleMix.class);
		System.out.println(beanExampleMix);

		System.out.println("END EXAMPLE_2" + System.lineSeparator());
	}

	private static void example_3(@NotNull BeanFactory context) {
		System.out.println("START EXAMPLE_3:");

		UserRepository userRepository = context.getBean(UserRepository.class);
		System.out.println("userRepository = " + userRepository);
		System.out.println("userRepository.getUserByName(DEFAULT_USER_NAME) = " + userRepository.getUserByName(DEFAULT_USER_NAME));
		System.out.println("userRepository.getUserByName(\"Антон\") = " + userRepository.getUserByName("Антон") + System.lineSeparator());

		UserService userServiceConstructor = context.getBean(UserServiceConstructor.BEAN_NAME, UserServiceConstructor.class);
		System.out.println("userServiceConstructor.getDefaultUser() = " + userServiceConstructor.getDefaultUser());
		System.out.println("userServiceConstructor.getUserByName(\"Игорь\") = " + userServiceConstructor.getUserByName("Игорь") + System.lineSeparator());

		UserService userServiceSetter = context.getBean(UserServiceSetter.BEAN_NAME, UserServiceSetter.class);
		System.out.println("userServiceSetter.getDefaultUser() = " + userServiceSetter.getDefaultUser());
		System.out.println("userServiceSetter.getUserByName(\"Игорь\") = " + userServiceSetter.getUserByName("Игорь"));

		System.out.println("END EXAMPLE_3" + System.lineSeparator());
	}

	private static void example_4(@NotNull ClassPathXmlApplicationContext context) {
		System.out.println("START EXAMPLE_4:");
		CompanyController companyController = context.getBean("companyController", CompanyController.class);
		System.out.println("companyController = " + companyController);

		System.out.println(companyController.getAllCompany());

		System.out.println("END EXAMPLE_4" + System.lineSeparator());
	}
}
