package ru.otr.learn.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.otr.learn.entity.User;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// Настройка безопасности
		http
				// Отключаем CSRF
				.csrf(AbstractHttpConfigurer::disable)
				// Настройка прав доступа
				.authorizeHttpRequests(auth -> auth
						// Разрешаем доступ только админам
						.requestMatchers("/admin/**").hasAuthority(User.Role.ADMIN.getAuthority())
						// Разрешаем доступ к странице логина, регистрации и ресурсам
						.requestMatchers("/login", "/users/register", "/resources/**", "v3/api-docs/**", "/swagger-ui/**").permitAll()
						//.requestMatchers("/users/*/delete/**").hasAuthority(User.Role.ADMIN.getAuthority()) // Разрешаем удаление только админам
						// Остальные запросы требуют аутентификации
						.anyRequest().authenticated()
				)
				//.httpBasic(Customizer.withDefaults()) // Разрешаем HTTP Basic аутентификацию
				.formLogin(form -> form // Настройка формы логина
						.loginPage("/login") // Переопределяем URL страницы логина
						.permitAll() // Разрешаем всем доступ к странице логина
						.defaultSuccessUrl("/", true) // Устанавливаем страницу после успешного входа
						.failureUrl("/login?error=true") // Указываем URL на случай ошибки аутентификации
				)
				.logout(logout -> logout // Настройка выхода
						.logoutUrl("/logout") // Переопределяем URL выхода
						.logoutSuccessUrl("/") // Указываем URL на случай успешного выхода
				);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
