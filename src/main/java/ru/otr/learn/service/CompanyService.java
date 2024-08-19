package ru.otr.learn.service;

import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import ru.otr.learn.dto.CompanyDto;
import ru.otr.learn.entity.Company;
import ru.otr.learn.listener.entity.EntityEvent;
import ru.otr.learn.listener.entity.OperationType;
import ru.otr.learn.repository.impl.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Value
@NonFinal
@Slf4j
@Service
public class CompanyService implements ApplicationEventPublisherAware {

	CompanyRepository companyRepository;
	@NonFinal
	ApplicationEventPublisher applicationEventPublisher;

	public CompanyService(@Autowired CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	public @NotNull List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	public Optional<CompanyDto> getCompanyByName(String companyName) {
		return companyRepository.findByName(companyName).map(company -> {
			applicationEventPublisher.publishEvent(new EntityEvent<>(company, OperationType.READ));
			return new CompanyDto(company.getId(), company.getName(), company.getUsers());
		});
	}

	public Company createCompany(Company company) {
		Company createdCompany = companyRepository.saveAndFlush(company);
		applicationEventPublisher.publishEvent(new EntityEvent<>(createdCompany, OperationType.CREATE));
		return createdCompany;
	}

	public @Nullable Company deleteCompanyByName(String companyName) {
		return companyRepository.findByName(companyName).map(company -> {
			companyRepository.delete(company);
			applicationEventPublisher.publishEvent(new EntityEvent<>(company, OperationType.DELETE));
			return company;
		}).orElse(null);
	}

	@Override
	public void setApplicationEventPublisher(@NotNull ApplicationEventPublisher applicationEventPublisher) {
		// Тот же самый наш контекст AnnotationConfigApplicationContext
		this.applicationEventPublisher = applicationEventPublisher;
	}
}
