package ru.otr.learn.mapper;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import ru.otr.learn.dto.CompanyReadDto;
import ru.otr.learn.entity.Company;

@Component
public class CompanyReadMapper implements Mapper<Company, CompanyReadDto> {

	@Override
	public CompanyReadDto map(@Nullable Company company) {
		if (null == company) {
			return null;
		}
		return CompanyReadDto.builder()
				.id(company.getId())
				.name(company.getName())
				.build();
	}
}
