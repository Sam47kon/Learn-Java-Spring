package ru.otr.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.otr.learn.db.Database;
import ru.otr.learn.db.IdGenerator;
import ru.otr.learn.entity.SystemUser;

@Profile("prod")
@Configuration
public class WebApplicationConfiguration {

	@Bean("database5")
	public Database db5(){
		return new Database(new IdGenerator(), new SystemUser("4444", 0));
	}
}
