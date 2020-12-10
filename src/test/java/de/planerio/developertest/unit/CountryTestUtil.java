package de.planerio.developertest.unit;

import de.planerio.developertest.controller.commons.country.CountryRequest;
import de.planerio.developertest.controller.commons.country.CountryResponse;
import de.planerio.developertest.model.entities.Country;
import java.util.ArrayList;
import java.util.List;

public class CountryTestUtil {
  public static List<Country> createCountryResponseList() {
    final List<Country> countries = new ArrayList<>();

    Country created = createCountry(1L, "USA", "en");
    countries.add(created);

    created = createCountry(2L, "Canada", "en");
    countries.add(created);

    created = createCountry(3L, "Germany", "de");
    countries.add(created);

    return countries;
  }

  public static Country createCountry(Long id, String name, String language) {

    final Country country = new Country(id, name, language);
    return country;
  }

  public static CountryResponse createCountryResponse(Long id, String name, String language){
    return CountryResponse.builder()
        .id(id)
        .language(language)
        .name(name)
        .build();
  }

  public static CountryRequest createCountryRequest(String name, String language) {
    final CountryRequest request = new CountryRequest();
    request.setName(name);
    request.setLanguage(language);

    return request;
  }
}
