package ru.otr.learn.service.xmlbased;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.otr.learn.entity.Company;
import ru.otr.learn.repository.xmlbased.CompanyRepository;

import java.util.List;

public class CompanyService {

	private final CompanyRepository companyRepository;

	public CompanyService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	public @NotNull List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	public @Nullable Company getCompanyByName(String companyName) {
		return companyRepository.findByName(companyName);
	}
}
