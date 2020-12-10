package de.planerio.developertest.controller.commons.league;

import de.planerio.developertest.controller.commons.country.CountryResponse;
import de.planerio.developertest.controller.commons.team.TeamResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeagueResponse {

  private Long id;
  private String name;
  private CountryResponse country;
  private List<TeamResponse> teams;
}
