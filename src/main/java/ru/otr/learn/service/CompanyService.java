package ru.otr.learn.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otr.learn.entity.Company;
import ru.otr.learn.repository.impl.CompanyRepository;

import java.util.List;

@Service
public class CompanyService {

	private final CompanyRepository companyRepository;

	public CompanyService(@Autowired CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	public @NotNull List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	public @Nullable Company getCompanyByName(String companyName) {
		return companyRepository.findByName(companyName);
	}
}
