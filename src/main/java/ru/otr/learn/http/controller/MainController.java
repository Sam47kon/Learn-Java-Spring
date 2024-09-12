package ru.otr.learn.http.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.http.HttpHeaders.*;

@Controller
@RequestMapping
public class MainController {

	// http://localhost:8080/info
	//@GetMapping("/info/{id}")
	@GetMapping("/info")
	public ModelAndView info(@NotNull ModelAndView modelAndView,
							 // @PathVariable(value = "id", required = false) int id,
							 @RequestParam(value = "name", required = false) String name,
							 @RequestHeader(value = USER_AGENT, required = false) String userAgent,
							 @RequestHeader(value = ACCEPT, required = false) String accept,
							 @RequestHeader(value = ACCEPT_LANGUAGE, required = false) String acceptLanguage,
							 @RequestHeader(value = ACCEPT_CHARSET, required = false) String acceptCharset,
							 @RequestHeader(value = ACCEPT_ENCODING, required = false) String acceptEncoding,
							 @RequestHeader(value = COOKIE, required = false) String cookie,
							 HttpServletRequest request,
							 HttpServletResponse response) {
		String acceptHeader = request.getHeader(ACCEPT);
		Cookie[] cookies = request.getCookies();
		modelAndView.setViewName("start-page");
		return modelAndView;
	}

	// http://localhost:8080/start-page
	@GetMapping("/start-page")
	public String hello(ModelAndView modelAndView,
						Model model,
						HttpServletRequest request,
						HttpServletResponse response) {
		return "start-page";
	}
}
