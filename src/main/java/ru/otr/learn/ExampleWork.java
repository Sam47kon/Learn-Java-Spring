package ru.otr.learn;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import ru.otr.learn.configuration.DbProperties;
import ru.otr.learn.entity.User;
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
	EntityManagerFactory entityManagerFactory;
	TransactionManager transactionManager;
	TransactionTemplate transactionTemplate;

	@NonFinal
	ApplicationContext context;

	public void example_5_1() {
		DbProperties dbProperties = context.getBean(DbProperties.class);
		log.debug("dbProperties = {}", dbProperties);
	}

	@Transactional
	public void example_5_2() {
		log.debug("userService.getAllUsers():\n\t{}{}", prettyList(userService.getAllUsers()), System.lineSeparator());
	}

	@Transactional
	public void example_5_3() {
		log.debug("companyService.getAllCompanies():\n\t{}{}", prettyList(companyService.getAllCompanies()), System.lineSeparator());

		log.debug("companyService.getCompanyByName(\"ОТР\") = {}{}", companyService.getCompanyByName("ОТР"), System.lineSeparator());
	}

	public void example_6_1() {
		/* Лишь пример как можно пользоваться EntityManagerFactory/EntityManager. В Spring так не получится
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
		EntityManager em = emf.createEntityManager();
		*/

		EntityManager em = entityManagerFactory.createEntityManager();

		// Начало транзакции
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		try {
			// Создание новой сущности
			User user = new User();
			user.setName("Алина 1");
			user.setAge(27);

			// Сохранение сущности
			em.persist(user);

			// Коммит транзакции
			transaction.commit();
		} catch (Exception e) {
			// Откат транзакции в случае ошибки
			transaction.rollback();
		} finally {
			// Закрытие EntityManager
			em.close();
			// emf.close(); для примера
		}


		User user = userService.getUserByName("Алина 1").orElse(null);
		log.debug("Созданный пользователь: {}", user);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public void example_6_2() {
		User createdUser = User.builder().name("Алина 2").age(27).build();
		userService.createUser(createdUser);

		log.debug("Созданный пользователь: {}", createdUser);
	}

	public void example_6_3() {
		transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		transactionTemplate.setReadOnly(true);
		transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		transactionTemplate.setTimeout(10); // Секунд

		try {
			transactionTemplate.executeWithoutResult(status -> {
				log.debug("Попытка создать пользователя");
				User createdUser = User.builder().name("Алина 3").age(27).build();
				userService.createUser(createdUser);
				log.debug("Создали пользователя {}", createdUser.getId());

				if (status.isReadOnly()) {
					log.error("Откат транзакции");
					throw new UnsupportedOperationException("Невозможно выполнить операцию записи в read-only транзакции.");
				}
			});
		} catch (Exception e) {
			log.error("Не удалось создать пользователя, ошибка: {}", e.getMessage());
		}

		User user = userService.getUserByName("Алина 3").orElse(null);
		log.debug("Созданный пользователь: {}", user);
	}

	@Transactional(timeout = 2)
	@SneakyThrows
	public void example_6_4() {
		log.info("userService.getUserByName(\"Илья\") = {}", userService.getUserByName("Илья"));
		log.debug("Ожидаем...");
		Thread.sleep(5_000);
		log.debug("Подождали 5 секунд");
		log.info("userService.getUserByName(\"Антон\") = {}", userService.getUserByName("Антон"));
	}

	@Override
	public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}
