package ru.otr.learn.ioc.crud.controller;

import ru.otr.learn.ioc.crud.bpp.InjectBean;
import ru.otr.learn.ioc.crud.entity.Company;
import ru.otr.learn.ioc.crud.service.CompanyService;

import java.util.List;

public class CompanyController {

	@InjectBean
	private CompanyService companyService;

	public List<Company> getAllCompany() {
		return companyService.getAll();
	}
}
