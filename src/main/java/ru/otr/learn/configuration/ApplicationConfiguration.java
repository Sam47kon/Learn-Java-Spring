package ru.otr.learn.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.otr.learn.db.Database;
import ru.otr.learn.db.IdGenerator;
import ru.otr.learn.entity.SystemUser;

@Configuration
public class ApplicationConfiguration {

	@Profile("web")
	@Bean("database2")
	public Database database2(@Value("${systemUser.web.name}") String name) {
		return new Database(new IdGenerator(), new SystemUser(name, 0));
	}

	@Bean(name = "database3")
	public Database database3(@Value("${systemUser.system.name}") String name) {
		return new Database(new IdGenerator(), new SystemUser(name, 0));
	}
}

