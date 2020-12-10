package de.planerio.developertest.model.repository;

import de.planerio.developertest.model.entities.Player;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {

  List<Player> findByPosition(String position);

  List<Player> findByPositionIn(List<String> positions);

  Optional<Player> findByShirtNumberAndTeamId(Integer shirtNumber, Long teamId);

  Integer countPlayersByTeamId(Long teamId);

  Optional<Boolean> existsPlayerById(Long id);
}
