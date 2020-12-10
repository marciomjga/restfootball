package de.planerio.developertest.model.repository;

import de.planerio.developertest.model.entities.Country;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {
  Country findCountryById(Long id);

  Optional<Boolean> existsCountryById(Long id);

  Optional<Boolean> existsCountryByNameAndLanguage(String name, String language);
}
