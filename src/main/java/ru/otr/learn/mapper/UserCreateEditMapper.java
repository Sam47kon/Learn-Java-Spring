package ru.otr.learn.mapper;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.otr.learn.dto.UserCreateEditDto;
import ru.otr.learn.entity.Company;
import ru.otr.learn.entity.User;
import ru.otr.learn.repository.impl.CompanyRepository;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

	private final CompanyRepository companyRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public User transform(@NotNull UserCreateEditDto from) {
		User user = new User();
		copy(from, user);
		return user;
	}

	@Override
	public User transform(@NotNull UserCreateEditDto from, @NotNull User user) {
		copy(from, user);
		return user;
	}

	private void copy(@NotNull UserCreateEditDto from, @NotNull User user) {
		user.setUsername(from.getUsername());
		user.setName(from.getName());
		user.setAge(from.getAge());
		user.setBirthDate(from.getBirthDate());
		user.setRole(from.getRole());
		user.setCompany(getCompany(from.getCompanyId()));

		Optional.ofNullable(from.getSourcePassword())
				.filter(StringUtils::hasText)
				.map(passwordEncoder::encode)
				.ifPresent(user::setPassword);
	}

	private Company getCompany(Long companyId) {
		return Optional.ofNullable(companyId)
				.flatMap(companyRepository::findById)
				.orElse(null);
	}
}
