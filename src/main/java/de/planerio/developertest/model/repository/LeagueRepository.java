package de.planerio.developertest.model.repository;

import de.planerio.developertest.model.entities.League;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends CrudRepository<League, Long> {
  List<League> findByCountryLanguage(String countryLanguage);

  Optional<League> findByCountryId(Long countryId);

  Optional<Boolean> existsLeagueById(Long id);

  Optional<Boolean> existsLeagueByNameAndCountryId(String name, Long countryId);
}
