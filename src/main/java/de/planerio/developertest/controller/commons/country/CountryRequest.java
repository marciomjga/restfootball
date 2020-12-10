package de.planerio.developertest.controller.commons.country;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryRequest {

  @NotBlank(message = "`name` is required")
  private String name;

  @NotBlank(message = "`language` is required")
  private String language;
}
