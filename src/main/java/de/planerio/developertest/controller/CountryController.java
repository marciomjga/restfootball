package de.planerio.developertest.controller;

import de.planerio.developertest.controller.commons.country.CountryRequest;
import de.planerio.developertest.controller.commons.country.CountryResponse;
import de.planerio.developertest.controller.commons.exceptions.InvalidInputException;
import de.planerio.developertest.controller.commons.exceptions.ResourceNotFoundException;
import de.planerio.developertest.controller.resources.CountryResource;
import de.planerio.developertest.controller.validators.LanguageValidator;
import de.planerio.developertest.service.CountryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController implements CountryResource {

  private CountryService countryService;

  @Autowired
  public CountryController(CountryService countryService) {
    this.countryService = countryService;
  }

  @Override
  public CountryResponse createCountry(CountryRequest countryRequest) {
    validateCountryWithNameAndLanguageExists(
        countryRequest.getName(), countryRequest.getLanguage());
    return countryService.save(countryRequest);
  }

  @Override
  public CountryResponse updateCountry(Long id, CountryRequest countryRequest) {
    LanguageValidator.validateLanguage(countryRequest.getLanguage());
    validateCountryWithIdExists(id);
    return countryService.update(countryRequest, id);
  }

  @Override
  public CountryResponse deleteCountry(Long id) {
    validateCountryWithIdExists(id);
    return countryService.delete(id);
  }

  @Override
  public CountryResponse getCountryById(Long id) {
    validateCountryWithIdExists(id);
    return countryService.findById(id);
  }

  @Override
  public List<CountryResponse> getCountries() {
    return countryService.findAll();
  }

  private void validateCountryWithIdExists(Long id) {
    Boolean idExists = countryService.existsCountryWithId(id);

    if (!idExists) {
      throw new ResourceNotFoundException("Country", id.toString());
    }
  }

  private void validateCountryWithNameAndLanguageExists(String name, String language) {
    Boolean idExists = countryService.existsCountryWithNameAndLanguage(name, language);

    if (idExists) {
      throw new InvalidInputException(
          String.format("Country with name %s and Language %s already exists", name, language));
    }
  }
}
