package ru.otr.learn.service;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import ru.otr.learn.dto.CompanyDto;
import ru.otr.learn.dto.CompanyReadDto;
import ru.otr.learn.entity.Company;
import ru.otr.learn.listener.entity.EntityEvent;
import ru.otr.learn.listener.entity.OperationType;
import ru.otr.learn.mapper.CompanyReadMapper;
import ru.otr.learn.repository.impl.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Value
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@NonFinal
@Slf4j
@Service
@Transactional(readOnly = true)
public class CompanyService implements ApplicationEventPublisherAware {

	CompanyRepository companyRepository;
	CompanyReadMapper companyReadMapper;

	@NonFinal
	ApplicationEventPublisher applicationEventPublisher;

	public @NotNull List<CompanyReadDto> getAllCompanies() {
		return companyRepository.findAll().stream()
				.map(companyReadMapper::transform)
				.toList();
	}

	public Optional<CompanyDto> getCompanyByName(String companyName) {
		return companyRepository.findByName(companyName).map(company -> {
			applicationEventPublisher.publishEvent(new EntityEvent<>(company, OperationType.READ));
			return new CompanyDto(company.getId(), company.getName(), company.getUsers());
		});
	}

	@Transactional
	public Company createCompany(Company company) {
		Company createdCompany = companyRepository.saveAndFlush(company);
		applicationEventPublisher.publishEvent(new EntityEvent<>(createdCompany, OperationType.CREATE));
		return createdCompany;
	}

	@Transactional
	public @Nullable Company deleteCompanyByName(String companyName) {
		if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
			throw new UnsupportedOperationException("Невозможно выполнить операцию записи в read-only транзакции.");
		}
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
