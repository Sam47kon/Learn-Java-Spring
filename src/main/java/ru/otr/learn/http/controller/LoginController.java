package ru.otr.learn.http.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping
public class LoginController {

	// http://localhost:8080/
	@GetMapping
	public String startPage(Model model, HttpServletRequest request,
							@CurrentSecurityContext SecurityContext securityContext,
							@AuthenticationPrincipal UserDetails userDetails) {
		log.debug("Session ID: {}", request.getSession().getId());

		// Authentication может быть null (если в SecurityConfiguration не использована аутентификация для этого url)
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails user) {
			model.addAttribute("username", user.getUsername());
		}
		return "main/start-page";
	}

	// http://localhost:8080/login
	@GetMapping("/login")
	public String login() {
		return "main/login-page";
	}

	/*@PostMapping("/login-my")
	public String login(@ModelAttribute("loginDto") LoginDto loginDto) {
		Optional<UserReadDto> user = userService.getUserUsername(loginDto.getUsername());
		return user.map(userReadDto -> "redirect:/users/" + userReadDto.getId()).orElse(USER_NOT_FOUND_PAGE);
	}*/

	@PostMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:";
	}
}
