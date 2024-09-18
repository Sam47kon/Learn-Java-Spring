package ru.otr.learn.http.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otr.learn.dto.LoginDto;
import ru.otr.learn.dto.UserReadDto;
import ru.otr.learn.service.UserService;

import java.util.Optional;

import static ru.otr.learn.http.controller.UserController.USER_NOT_FOUND_PAGE;

@Slf4j
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@Controller
@RequestMapping
public class LoginController {

	UserService userService;

	// http://localhost:8080/login
	@GetMapping("/login")
	public String login() {
		return "main/login-page";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("loginDto") LoginDto loginDto, HttpServletRequest request) {
		log.debug("Session ID: {}", request.getSession().getId());
		Optional<UserReadDto> user = userService.getUserByLogin(loginDto.getLogin());

		//request.getSession().setAttribute(USER, user.get());
		return user.map(userReadDto -> "redirect:/users/" + userReadDto.getId()).orElse(USER_NOT_FOUND_PAGE);

	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:";
	}
}
