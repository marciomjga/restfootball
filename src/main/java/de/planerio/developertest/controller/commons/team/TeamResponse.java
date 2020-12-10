package de.planerio.developertest.controller.commons.team;

import de.planerio.developertest.controller.commons.league.LeagueResponse;
import de.planerio.developertest.controller.commons.player.PlayerRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamResponse {

  private Long id;
  private String name;
  private LeagueResponse league;
  private List<PlayerRequest> players;
}
