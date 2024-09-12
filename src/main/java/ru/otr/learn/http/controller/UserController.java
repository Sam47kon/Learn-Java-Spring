package ru.otr.learn.http.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.otr.learn.dto.UserCreateEditDto;
import ru.otr.learn.dto.UserReadDto;
import ru.otr.learn.entity.User;
import ru.otr.learn.exception.UserNotFoundException;
import ru.otr.learn.mapper.UserReadMapper;
import ru.otr.learn.service.UserService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final UserReadMapper userReadMapper;

	// http://localhost:8080/users
	@GetMapping
	public ResponseEntity<List<UserReadDto>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		List<UserReadDto> usersResult = users.stream()
				.map(userReadMapper::map)
				.toList();
		return ResponseEntity.ok(usersResult);
	}

	// http://localhost:8080/users/1
	@GetMapping("/{id}")
	public ResponseEntity<UserReadDto> getUserById(@PathVariable("id") Long id, Model model) {
		Optional<UserReadDto> user = userService.getUserById(id);
		if (user.isEmpty()) {
			throw new UserNotFoundException(id);
		}
		return ResponseEntity.ok(user.get());
	}

	// http://localhost:8080/users/?id=1&name=%D0%98%D0%BB%D1%8C%D1%8F&age=28&role=DEV
	@GetMapping("/")
	public ModelAndView getUser(ModelAndView modelAndView, @ModelAttribute UserReadDto userReadDto, Model model) {
		Optional<UserReadDto> user = userService.getUserById(userReadDto.getId());
		if (user.isEmpty()) {
			modelAndView.setViewName("user-not-found");
		} else {
			modelAndView.setViewName("user");
			modelAndView.addObject("user", user.get());
		}
		return modelAndView;
	}

	// http://localhost:8080/users/by-name/?name=%D0%98%D0%BB%D1%8C%D1%8F
	@GetMapping("/by-name/")
	public ModelAndView getUserByName(@RequestParam("name") String name, Model model) {
		Optional<UserReadDto> user = userService.getUserByName(name);
		if (user.isEmpty()) {
			return new ModelAndView("user-not-found");
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("user");
		modelAndView.addObject("user", user.get());
		return modelAndView;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public String createUser(@ModelAttribute UserCreateEditDto userReadDto) {
		UserReadDto user = userService.createUser(userReadDto);
		return "redirect:/users/" + user.getId();
	}

	@PutMapping("/{id}")
	public String updateUser(@PathVariable("id") Long id, @ModelAttribute UserCreateEditDto userCreateEditDto) {
		return userService.updateUser(id, userCreateEditDto)
				.map(user -> "redirect:/users/" + user.getId())
				.orElseThrow(() -> new UserNotFoundException(id));
	}

	@DeleteMapping("/{id}")
	public String deleteByName(@PathVariable("id") Long id) {
		if (!userService.deleteUserById(id)) {
			throw new UserNotFoundException(id);
		}
		return "redirect:/users";
	}
}
