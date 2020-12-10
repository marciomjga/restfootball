package de.planerio.developertest.controller.commons.team;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamRequest {

  @NotBlank(message = "`name` is required")
  private String name;

  @NotNull(message = "`leagueId` is required")
  private Long leagueId;
}
