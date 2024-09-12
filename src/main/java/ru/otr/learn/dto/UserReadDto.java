package ru.otr.learn.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import ru.otr.learn.entity.User;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserReadDto {

	Long id;
	String name;
	int age;
	User.Role role;
	@NonFinal
	CompanyReadDto company;
}
