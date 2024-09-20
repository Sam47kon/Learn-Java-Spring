package ru.otr.learn.mapper;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.otr.learn.dto.UserReadDto;
import ru.otr.learn.entity.User;

@Component
@AllArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

	private final CompanyReadMapper companyReadMapper;

	@Override
	public @NotNull UserReadDto transform(@NotNull User from) {
		return UserReadDto.builder()
				.id(from.getId())
				.username(from.getUsername())
				.name(from.getName())
				.age(from.getAge())
				.birthDate(from.getBirthDate())
				.role(from.getRole())
				.company(companyReadMapper.transform(from.getCompany()))
				.build();
	}
}
