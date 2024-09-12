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
	public @NotNull UserReadDto map(@NotNull User from) {
		return UserReadDto.builder()
				.id(from.getId())
				.name(from.getName())
				.age(from.getAge())
				.role(from.getRole())
				.company(companyReadMapper.map(from.getCompany()))
				.build();
	}
}
