package ru.otr.learn.service;

import org.jetbrains.annotations.NotNull;
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

@Service
public class CompanyService implements ApplicationEventPublisherAware {

	private final CompanyRepository companyRepository;
	private ApplicationEventPublisher applicationEventPublisher;

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

	@Override
	public void setApplicationEventPublisher(@NotNull ApplicationEventPublisher applicationEventPublisher) {
		// Тот же самый наш контекст AnnotationConfigApplicationContext
		this.applicationEventPublisher = applicationEventPublisher;
	}
}
