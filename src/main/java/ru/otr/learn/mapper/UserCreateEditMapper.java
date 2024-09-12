package ru.otr.learn.mapper;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.otr.learn.dto.UserCreateEditDto;
import ru.otr.learn.entity.Company;
import ru.otr.learn.entity.User;
import ru.otr.learn.repository.impl.CompanyRepository;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

	private final CompanyRepository companyRepository;

	@Override
	public User map(@NotNull UserCreateEditDto from) {
		return User.builder()
				.name(from.getName())
				.age(from.getAge())
				.role(from.getRole())
				.company(getCompany(from.getCompanyId()))
				.build();
	}

	@Override
	public User map(@NotNull UserCreateEditDto userCreateEditDto, @NotNull User user) {
		user.setName(userCreateEditDto.getName());
		user.setAge(userCreateEditDto.getAge());
		user.setRole(userCreateEditDto.getRole());
		user.setCompany(getCompany(userCreateEditDto.getCompanyId()));
		return user;
	}

	private Company getCompany(Long companyId) {
		return Optional.ofNullable(companyId)
				.flatMap(companyRepository::findById)
				.orElse(null);
	}
}
