package de.planerio.developertest.controller.commons.league;

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
public class LeagueRequest {

  @NotBlank(message = "`name` is required")
  private String name;

  @NotNull(message = "`countryId` is required")
  private Long countryId;
}
