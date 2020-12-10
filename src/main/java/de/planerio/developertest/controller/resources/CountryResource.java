package de.planerio.developertest.controller.resources;

import de.planerio.developertest.controller.commons.country.CountryRequest;
import de.planerio.developertest.controller.commons.country.CountryResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api("Resources for Country Manipulation")
@RequestMapping(value = CountryResource.RESOURCE_PATH,
    produces = MediaType.APPLICATION_JSON_VALUE)
public interface CountryResource {

  String RESOURCE_PATH = "/api/country";

  @ApiOperation("Create a new country")
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  CountryResponse createCountry(
      @ApiParam(value = "Country data from the object to be created", required = true)
      @RequestBody CountryRequest countryRequest);

  @ApiOperation("Update an existing country")
  @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  CountryResponse updateCountry(
      @ApiParam(value = "Id of the country to be updated", required = true)
      @PathVariable("id") Long id,

      @ApiParam(value = "Contry data from the object to be updated", required = true)
      @RequestBody CountryRequest countryRequest);

  @ApiOperation("Delete an existing country")
  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  CountryResponse deleteCountry(
      @ApiParam(value = "Id of the country to be deleted", required = true)
      @PathVariable("id") Long id);

  @ApiOperation("Return an Country given an id")
  @GetMapping(path = "/{id}")
  CountryResponse getCountryById(
      @ApiParam(value = "Id of the country to be retrieved", required = true) @PathVariable("id")
          Long id);

  @ApiOperation("Return all countries on the DB")
  @GetMapping
  List<CountryResponse> getCountries();
}
