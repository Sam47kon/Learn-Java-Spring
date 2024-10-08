package ru.otr.learn.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;
import ru.otr.learn.entity.User;
import ru.otr.learn.validation.group.CreateAction;

import java.time.LocalDate;

@Builder
@Value
public class UserCreateEditDto {

	// Можно создать свою аннотацию валидации, например @ExistsUsername с группой CreateAction,
	// и выполнять проверку логина в БД на уровне валидации, а не во время выполнения метода createUser в контроллере.
	@NotBlank(message = "Логин не может быть пустым")
	@Size(min = 3, max = 20, message = "Логин должен содержать от 3 до 20 символов")
	String username;

	@NotBlank(groups = CreateAction.class, message = "Пароль не может быть пустым")
	String sourcePassword;

	@NotBlank(message = "Имя не может быть пустым")
	@Size(min = 2, max = 50, message = "Имя должно содержать от 2 до 50 символов")
	String name;

	@Positive(message = "Возраст должен быть положительным")
	@Min(value = 18, message = "Возраст должен быть не менее 18 лет")
	@Max(value = 100, message = "Возраст не должен превышать 100 лет")
	Integer age;

	@Past(message = "Дата рождения должна быть в прошлом")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	LocalDate birthDate;

	@NotNull(message = "Роль должна быть указана")
	User.Role role;

	@Positive(message = "ID компании должен быть положительным числом")
	Long companyId;
}
