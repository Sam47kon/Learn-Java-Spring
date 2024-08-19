package ru.otr.learn.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public final class Company extends BaseEntity {
	String name;
	List<User> users;

	public Company(String name, List<User> users) {
		this.name = name;
		this.users = users;
	}
}
