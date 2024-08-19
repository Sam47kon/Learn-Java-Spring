package ru.otr.learn.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Component;
import ru.otr.learn.db.Database;
import ru.otr.learn.db.IdGenerator;
import ru.otr.learn.entity.SystemUser;
import ru.otr.learn.repository.CrudRepository;
import ru.otr.web.WebApplicationConfiguration;


/*Для комбинации xml+java*/
//@ImportResource("classpath:xml-based.xml")

/* Для подключения других конфиг классов, например: в других пакетах (basePackages) или даже в других jar */
@Import(WebApplicationConfiguration.class)

@Configuration
@PropertySource(value = "classpath:application.properties")
@ComponentScan(basePackages = "ru.otr.learn",
		resourcePattern = "**/*.class",
		useDefaultFilters = false,
		includeFilters = {
				@Filter(type = FilterType.ANNOTATION, value = Component.class),
				@Filter(type = FilterType.ASSIGNABLE_TYPE, value = CrudRepository.class),
				@Filter(type = FilterType.REGEX, pattern = "\\.bpp\\.+")
		}
)
public class ApplicationConfiguration {

	@Profile("web|prod")
	@Bean("database2")
	public Database database2(@Value("${systemUser.web.name}") String name) {
		return new Database(new IdGenerator(), new SystemUser(name, 0));
	}

	@Bean(name = "database3")
	public Database database3(@Value("${systemUser.system.name}") String name) {
		return new Database(new IdGenerator(), new SystemUser(name, 0));
	}
}

