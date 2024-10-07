package ru.otr.learn.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otr.learn.entity.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	Optional<Company> findByName(String companyName);

	@EntityGraph(attributePaths = "users")
	Page<Company> findAll(Pageable pageable); // <>

	@Query("select c from Company c join fetch c.users u where u.username = :username")
	List<Company> findAllByUsername(String username);
}
