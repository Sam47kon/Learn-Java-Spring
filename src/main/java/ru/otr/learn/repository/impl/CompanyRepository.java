package ru.otr.learn.repository.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.otr.learn.db.Database;
import ru.otr.learn.entity.Company;
import ru.otr.learn.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CompanyRepository implements CrudRepository<Company> {

	private final Database database;

	public CompanyRepository(@Autowired @Qualifier(value = "database") Database database) {
		this.database = database;
	}

	@Override
	public @NotNull List<Company> findAll() {
		return new ArrayList<>(database.getCOMPANIES());
	}

	@Override
	public Optional<Company> findByName(String companyName) {
		return database.getCOMPANIES().stream().filter(company -> companyName.equals(company.getName())).findFirst();
	}

	@Override
	public Company create(Company company) {
		return database.createCompany(company);
	}

	@Override
	public void delete(Company company) {
		database.getCOMPANIES().remove(company);
	}
}
