package ru.otr.learn.repository;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public interface CrudRepository<E> {

	@NotNull
	List<E> findAll();

	@Nullable
	E findByName(String name);

	E create(E entity);

	void delete(E entity);
}
