package ru.otr.learn.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemUser extends User {
	public SystemUser(@Value(value = "#{systemProperties['user.name']}") String name,
					  @Value("${systemUser.age}") int age) {
		super(name, age);
	}
}
