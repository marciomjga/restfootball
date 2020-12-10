package de.planerio.developertest.controller.commons.player;

import de.planerio.developertest.controller.commons.team.TeamResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerResponse {

  private Long id;
  private String name;
  private TeamResponse team;
  private String position;
  private Integer shirtNumber;
}
