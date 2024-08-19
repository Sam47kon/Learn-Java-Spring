package ru.otr.learn.entity.annotationbased;

import org.springframework.beans.factory.annotation.Value;
import ru.otr.learn.entity.User;

public class SystemUser extends User {
	public SystemUser(@Value(value = "#{systemProperties['user.name']}") String name,
					  @Value("#{new java.util.Random().nextInt(60 - 20 + 1) + 20}") int age) {
		super(name, age);
	}
}
