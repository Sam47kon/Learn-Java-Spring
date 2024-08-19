package ru.otr.learn.entity.javabased;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otr.learn.entity.User;

@Component
public class SystemUser extends User {
	public SystemUser(@Value(value = "#{systemProperties['user.name']}") String name,
					  @Value("#{new java.util.Random().nextInt(60 - 20 + 1) + 20}") int age) {
		super(name, age);
	}
}
