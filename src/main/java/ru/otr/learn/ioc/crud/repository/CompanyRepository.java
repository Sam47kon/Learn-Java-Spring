package ru.otr.learn.ioc.crud.repository;

import jakarta.annotation.PostConstruct;
import ru.otr.learn.ioc.crud.db.Database;
import ru.otr.learn.ioc.crud.entity.Company;

import java.util.ArrayList;
import java.util.List;

public class CompanyRepository {

	public List<Company> findAll() {
		return new ArrayList<>(Database.COMPANIES.values());
	}

	@PostConstruct
	public void init() {
		Database.initCompanies();
	}
}
