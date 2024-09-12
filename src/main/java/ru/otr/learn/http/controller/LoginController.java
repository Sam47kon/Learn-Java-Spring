package ru.otr.learn.http.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otr.learn.dto.LoginDto;

@Controller
@RequestMapping("/login")
public class LoginController {

	@GetMapping
	public String login() {
		return "login-page";
	}

	@PostMapping
	public String login(@ModelAttribute("login") LoginDto loginDto, Model model) {
		model.addAttribute("login", loginDto);
		return "redirect:/start-page";
	}
}
