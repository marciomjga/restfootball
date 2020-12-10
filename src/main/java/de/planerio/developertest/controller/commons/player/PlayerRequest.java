package de.planerio.developertest.controller.commons.player;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
public class PlayerRequest {

  @NotBlank(message = "`name` is required")
  private String name;

  @NotNull(message = "`teamId` is required")
  private Long teamId;

  @NotNull(message = "`position` is required")
  private String position;

  @Min(value = 1, message = "`shirtNumber` must be greater or equals than 1")
  @Max(value = 99, message = "`shirtNumber` must be less or equals than 99")
  private Integer shirtNumber;
}
