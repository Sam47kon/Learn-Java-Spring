package ru.otr.learn.http.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otr.learn.dto.UserCreateEditDto;
import ru.otr.learn.dto.UserReadDto;
import ru.otr.learn.exception.UserNotFoundException;
import ru.otr.learn.service.UserService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

	private final UserService userService;

	// http://localhost:8080/api/v1/users
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserReadDto>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	// http://localhost:8080/api/v1/users/1
	@GetMapping("/{id}")
	public ResponseEntity<UserReadDto> getUserById(@PathVariable("id") Long id) {
		Optional<UserReadDto> user = userService.getUserById(id);
		if (user.isEmpty()) {
			throw new UserNotFoundException(id);
		}
		return ResponseEntity.ok(user.get());
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<UserReadDto> createUser(@RequestBody UserCreateEditDto userReadDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userReadDto));
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReadDto> updateUser(@PathVariable("id") Long id, @RequestBody UserCreateEditDto userCreateEditDto) {
		Optional<UserReadDto> user = userService.updateUser(id, userCreateEditDto);
		if (user.isEmpty()) {
			throw new UserNotFoundException(id);
		} else {
			return ResponseEntity.ok(user.get());
		}
	}

	@DeleteMapping("/{id}/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable("id") Long id) {
		if (!userService.deleteUserById(id)) {
			throw new UserNotFoundException(id);
		}
	}
}
