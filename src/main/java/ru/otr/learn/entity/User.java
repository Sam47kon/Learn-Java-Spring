package ru.otr.learn.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@ToString(callSuper = true, includeFieldNames = false)
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class User extends BaseEntity {
	String name;
	int age;

	public User(String name, int age) {
		this.name = name;
		this.age = age;
	}
}
