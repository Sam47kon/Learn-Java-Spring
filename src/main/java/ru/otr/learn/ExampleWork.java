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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import ru.otr.learn.configuration.DbProperties;
import ru.otr.learn.entity.Company;
import ru.otr.learn.entity.User;
import ru.otr.learn.entity.User.Role;
import ru.otr.learn.repository.impl.CompanyRepository;
import ru.otr.learn.repository.impl.UserRepository;
import ru.otr.learn.service.CompanyService;
import ru.otr.learn.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.otr.learn.utils.Utils.prettyList;

@Slf4j
@Value
@NonFinal
@Component
public class ExampleWork implements ApplicationContextAware {

	UserService userService;
	UserRepository userRepository;
	CompanyService companyService;
	CompanyRepository companyRepository;

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

	public void example_7_1() {
		log.debug("findById(1):");
		Optional<User> userOpt = userRepository.findById(1L);
		userOpt.ifPresentOrElse(
				this::logUser,
				this::logUserNotFound);
		System.out.println();
	}

	public void example_7_2_NamedQuery() {
		log.debug("NamedQuery: findByNameAndAgeGreaterThanNamed(\"Илья\", 25):");
		Optional<User> userOpt = userRepository.findByNameAndAgeGreaterThanNamed("Илья", 25);
		userOpt.ifPresentOrElse(
				this::logUser,
				this::logUserNotFound);
		System.out.println();
	}

	public void example_7_3_NativeQuery() {
		log.debug("NativeQuery: findByNameAndAgeGreaterThanNative(\"Илья\", 25):");
		Optional<User> userOpt = userRepository.findByNameAndAgeGreaterThanNative("Илья", 25);
		userOpt.ifPresentOrElse(
				this::logUser,
				this::logUserNotFound);
		System.out.println();
	}

	public void example_7_4_SimpleJpaQuery() {
		log.debug("SimpleJpaQuery: findByNameAndAgeGreaterThanSimple(\"Илья\", 25):");
		Optional<User> userOpt = userRepository.findByNameAndAgeGreaterThanSimple("Илья", 25);
		userOpt.ifPresentOrElse(
				this::logUser,
				this::logUserNotFound);
		System.out.println();
	}

	public void example_7_5_PartTreeJpaQuery() {
		log.debug("PartTreeJpaQuery: findByNameAndAgeGreaterThanEqual(\"Илья\", 25):");
		Optional<User> userOpt = userRepository.findByNameAndAgeGreaterThanEqual("Илья", 25);
		userOpt.ifPresentOrElse(
				this::logUser,
				this::logUserNotFound);
		System.out.println();
	}

	@Transactional
	public void example_7_6_Update() {
		log.debug("findUsersByRole(Role.QA):");
		List<User> usersQA = userRepository.findUsersByRole(Role.QA);
		log.debug("usersQA = {}", usersQA);

		log.debug("updateRole(Role.DEV, ids):");
		int count = userRepository.updateRole(Role.DEV, usersQA.stream().map(User::getId).collect(Collectors.toList()));
		log.debug("count = {}", count);

		log.debug("ids findUsersByRole(Role.QA):");
		List<User> usersQA2 = userRepository.findUsersByRole(Role.QA);
		log.debug("usersQA2 = {}", usersQA2);

		System.out.println();
	}

	public void example_7_7_DynamicSort() {
		log.debug("findTop3ByRoleOrderByAgeDesc(Role.DEV):");
		List<User> users = userRepository.findTop3ByRoleOrderByAgeDesc(Role.DEV);
		log.debug("users = {}", users);

		log.debug("findTop3ByRole(Role.DEV, Sort.by(\"age\").descending()):");
		users = userRepository.findTop3ByRole(Role.DEV, Sort.by("age").descending());
		log.debug("users = {}", users);

		log.debug("findTop3ByRole(Role.DEV, Sort.sort(User.class).by(User::getAge).descending()):");
		users = userRepository.findTop3ByRole(Role.DEV, Sort.sort(User.class).by(User::getAge).descending());
		log.debug("users = {}", users);

		System.out.println();
	}

	public void example_7_8_Page() {
		log.debug("Все пользователи: \n\t{}", userRepository.findAll());

		log.debug("findAll:");
		Page<User> page = userRepository.findAll(PageRequest.of(0, 2, Sort.by("age").descending()));
		log.debug("page = {}\n\t{}", page, page.getContent());

		while (page.hasNext()) {
			page = userRepository.findAll(page.nextPageable());
			log.debug("page = {}\n\t{}", page, page.getContent());
		}

		System.out.println();
	}

	public void example_7_9_Slice() {
		log.debug("findAllUsers:");
		Slice<User> slice = userRepository.findAllBy(PageRequest.of(0, 2, Sort.by("age").descending()));
		log.debug("slice = {}\n\t{}", slice, slice.getContent());

		while (slice.hasNext()) {
			slice = userRepository.findAllBy(slice.nextPageable());
			log.debug("slice = {}\n\t{}", slice, slice.getContent());
		}

		System.out.println();
	}

	@Transactional
	public void example_7_10_EntityGraph() {
		log.debug("findAll:");
		Page<Company> page = companyRepository.findAll(PageRequest.of(0, 2, Sort.sort(Company.class).by(Company::getId)));
		log.debug("page = {}", page);

		log.debug("page.getContent():");
		log.debug("content = \n\t{}", page.getContent());

		while (page.hasNext()) {
			log.debug("findAll:");
			page = companyRepository.findAll(page.nextPageable());
			log.debug("page = {}", page);

			log.debug("page.getContent():");
			log.debug("content = \n\t{}", page.getContent());
		}

		System.out.println();
	}

	private void logUser(User user) {
		log.debug("user = {}", user);
	}

	private void logUserNotFound() {
		log.debug("Пользователь не найден");
	}

	@Override
	public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}
