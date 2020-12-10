package de.planerio.developertest.controller.validators;

import de.planerio.developertest.controller.commons.country.CountryLanguage;
import de.planerio.developertest.controller.commons.exceptions.InvalidInputException;
import java.util.Arrays;

public class LanguageValidator {
  public static void validateLanguage(String languageParam) {
    Boolean languageIsValid =
        Arrays.stream(CountryLanguage.values())
            .anyMatch(language -> language.name().toLowerCase().equals(languageParam));

    if (!languageIsValid) {
      throw new InvalidInputException(
          String.format(
              "%s is an invalid language. The possible values are %s",
              languageParam, Arrays.toString(CountryLanguage.values())));
    }
  }
}
