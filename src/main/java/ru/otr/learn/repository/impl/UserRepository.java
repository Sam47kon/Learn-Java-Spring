package ru.otr.learn.repository.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otr.learn.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	@NotNull List<User> findAll();

	Optional<User> findByName(String userName);
}
