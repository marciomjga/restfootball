package de.planerio.developertest.model.repository;

import de.planerio.developertest.model.entities.Team;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {

  Optional<Boolean> existsTeamById(Long id);

  Optional<Boolean> existsTeamByNameAndLeagueId(String name, Long id);

  Integer countTeamsByLeagueId(Long leagueId);
}
