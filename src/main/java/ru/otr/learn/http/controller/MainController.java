package ru.otr.learn.http.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.otr.learn.service.CompanyService;

@AllArgsConstructor
@Slf4j
@Controller
@RequestMapping
public class MainController {

	public static final String SESSION_ID = "sessionId";

	private final CompanyService companyService;

	// http://localhost:8080/
	@GetMapping
	public String startPage(ModelAndView modelAndView,
							Model model,
							HttpServletRequest request,
							HttpServletResponse response) {
		String sessionId = request.getSession().getId();
		log.debug("Session ID: {}", sessionId);
		request.getSession().setAttribute(SESSION_ID, sessionId);
		model.addAttribute(SESSION_ID, sessionId);
		return "main/start-page";
	}

	@PostMapping
	public String toLoginPage(ModelAndView modelAndView,
							  Model model,
							  HttpServletRequest request,
							  HttpServletResponse response) {
		return "redirect:/login";
	}
}
