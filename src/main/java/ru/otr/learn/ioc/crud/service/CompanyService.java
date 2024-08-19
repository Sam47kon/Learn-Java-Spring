package ru.otr.learn.ioc.crud.service;

import ru.otr.learn.ioc.crud.bpp.InjectBean;
import ru.otr.learn.ioc.crud.entity.Company;
import ru.otr.learn.ioc.crud.repository.CompanyRepository;

import java.util.List;

public class CompanyService {

	@InjectBean
	private CompanyRepository companyRepository;

	public List<Company> getAll() {
		return companyRepository.findAll();
	}
}
