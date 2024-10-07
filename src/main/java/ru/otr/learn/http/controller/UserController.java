package ru.otr.learn.http.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.otr.learn.dto.UserCreateEditDto;
import ru.otr.learn.dto.UserReadDto;
import ru.otr.learn.entity.User;
import ru.otr.learn.exception.UserNotFoundException;
import ru.otr.learn.service.CompanyService;
import ru.otr.learn.service.UserService;
import ru.otr.learn.utils.Utils;
import ru.otr.learn.validation.group.CreateAction;
import ru.otr.learn.validation.group.UpdateAction;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {

	public static final String USERS = "users";
	public static final String USERS_PAGE = "user/users-page";
	public static final String USER = "user";
	public static final String USER_PAGE = "user/user-page";
	public static final String USER_NOT_FOUND_PAGE = "user/user-not-found";

	private final UserService userService;
	private final CompanyService companyService;

	// http://localhost:8080/users
	@PostAuthorize("returnObject")
	@GetMapping
	public String getAllUsers(Model model,  @CurrentSecurityContext SecurityContext securityContext,
							  @AuthenticationPrincipal UserDetails userDetails) {
		List<UserReadDto> users = userService.getAllUsers();
		model.addAttribute(USERS, users);
		return USERS_PAGE;
	}

	// http://localhost:8080/users/1
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/{id}")
	public ModelAndView findById(ModelAndView modelAndView, @PathVariable("id") Long id, HttpServletRequest request) {
		log.debug("Session ID: {}", request.getSession().getId());

		userService.getUserById(id).ifPresentOrElse(user -> {
			modelAndView.setViewName(USER_PAGE);
			modelAndView.addObject(USER, user);
			modelAndView.addObject("roles", User.Role.values());
			modelAndView.addObject("companies", companyService.getAllCompanies());
		}, () -> {
			modelAndView.setViewName(USER_NOT_FOUND_PAGE);
			modelAndView.addObject("userId", id);
		});
		return modelAndView;
	}

	// http://localhost:8080/users/register
	@GetMapping("/register")
	public String createUser(Model model, @ModelAttribute(USER) UserCreateEditDto user) {
		model.addAttribute(USER, user);
		model.addAttribute("roles", User.Role.values());
		model.addAttribute("companies", companyService.getAllCompanies());
		return "main/register";
	}

	@PostMapping("/register")
	public String createUser(@ModelAttribute @Validated({Default.class, CreateAction.class}) UserCreateEditDto userReadDto,
							 BindingResult bindingResult, // Ставить перед RedirectAttributes
							 RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			log.debug("Binding errors: {}", Utils.prettyList(bindingResult.getAllErrors()));
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			redirectAttributes.addFlashAttribute(USER, userReadDto);
			return "redirect:/users/register";
		}
		UserReadDto user = userService.createUser(userReadDto);
		return "redirect:/users/" + user.getId();
	}

	@PostMapping("/{id}/edit")
	public String updateUser(@PathVariable("id") Long id,
							 @ModelAttribute @Validated({Default.class, UpdateAction.class}) UserCreateEditDto userCreateEditDto) {
		return userService.updateUser(id, userCreateEditDto)
				.map(user -> "redirect:/users/" + user.getId())
				.orElseThrow(() -> new UserNotFoundException(id));
	}

	@PostMapping("/{id}/delete")
	public String deleteById(@PathVariable("id") Long id) {
		if (!userService.deleteUserById(id)) {
			throw new UserNotFoundException(id);
		}
		return "redirect:/users";
	}
}
