package ru.otr.learn.dto;

import lombok.Builder;
import lombok.Value;
import ru.otr.learn.entity.User;

@Builder
@Value
public class UserCreateEditDto {

	String name;
	int age;
	User.Role role;
	Long companyId;
}
