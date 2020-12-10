package de.planerio.developertest.service;

import de.planerio.developertest.controller.commons.country.CountryRequest;
import de.planerio.developertest.controller.commons.country.CountryResponse;
import de.planerio.developertest.controller.commons.exceptions.ResourceNotFoundException;
import de.planerio.developertest.model.entities.Country;
import de.planerio.developertest.model.repository.CountryRepository;
import de.planerio.developertest.service.converters.CountryConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

  private final CountryRepository countryRepository;
  private final CountryConverter countryConverter;

  @Autowired
  public CountryService(CountryRepository countryRepository, CountryConverter countryConverter) {
    this.countryRepository = countryRepository;
    this.countryConverter = countryConverter;
  }

  public List<CountryResponse> findAll() {
    List<CountryResponse> allCountries = new ArrayList<>();
    countryRepository
        .findAll()
        .forEach(country -> allCountries.add(countryConverter.map(country, CountryResponse.class)));
    return allCountries;
  }

  public CountryResponse findById(Long countryId) {
    return countryConverter.map(
        countryRepository.findCountryById(countryId), CountryResponse.class);
  }

  public CountryResponse save(CountryRequest countryRequest) {

    Country country =
        Country.builder()
            .language(countryRequest.getLanguage())
            .name(countryRequest.getName())
            .build();
    country = countryRepository.save(country);
    return countryConverter.map(country, CountryResponse.class);
  }

  public CountryResponse update(CountryRequest countryRequest, Long countryId) {
    countryRepository
        .findById(countryId)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    Country.class.getSimpleName(), String.valueOf(countryId)));

    Country country =
        Country.builder()
            .id(countryId)
            .name(countryRequest.getName())
            .language(countryRequest.getLanguage())
            .build();

    return countryConverter.map(countryRepository.save(country), CountryResponse.class);
  }

  public CountryResponse delete(Long countryId) {
    Optional<Country> optionalCountry =
        Optional.ofNullable(countryRepository.findCountryById(countryId));

    if (optionalCountry.isPresent()) {
      countryRepository.deleteById(countryId);
      return countryConverter.map(optionalCountry.get(), CountryResponse.class);
    }

    throw new ResourceNotFoundException(Country.class.getSimpleName(), String.valueOf(countryId));
  }

  public Boolean existsCountryWithId(Long id) {
    return countryRepository.existsCountryById(id).orElse(Boolean.FALSE);
  }

  public Boolean existsCountryWithNameAndLanguage(String name, String language) {
    return countryRepository.existsCountryByNameAndLanguage(name, language).orElse(Boolean.FALSE);
  }
}
