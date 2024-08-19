package ru.otr.learn.repository.annotationbased;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.otr.learn.bpp.InjectBean;
import ru.otr.learn.db.annotationbased.Database;
import ru.otr.learn.entity.Company;
import ru.otr.learn.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public class CompanyRepository implements CrudRepository<Company> {

	private final Database database;

	public CompanyRepository(Database database) {
		this.database = database;
	}

	@Override
	public @NotNull List<Company> findAll() {
		return new ArrayList<>(database.getCOMPANIES());
	}

	@Override
	public @Nullable Company findByName(String companyName) {
		return database.getCOMPANIES().stream().filter(company -> companyName.equals(company.getName())).findFirst().orElse(null);
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
