package ru.otr.learn.repository;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<E> {

	@NotNull
	List<E> findAll();

	Optional<E> findByName(String name);

	E create(E entity);

	void delete(E entity);
}
