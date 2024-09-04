package ru.otr.learn.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otr.learn.entity.Company;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	Optional<Company> findByName(String companyName);

	@EntityGraph(attributePaths = "users")
	Page<Company> findAll(Pageable pageable); // <>
}
