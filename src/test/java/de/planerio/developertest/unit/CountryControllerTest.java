package de.planerio.developertest.unit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import de.planerio.developertest.controller.CountryController;
import de.planerio.developertest.controller.commons.country.CountryRequest;
import de.planerio.developertest.controller.commons.country.CountryResponse;
import de.planerio.developertest.controller.commons.exceptions.InvalidInputException;
import de.planerio.developertest.controller.commons.exceptions.ResourceNotFoundException;
import de.planerio.developertest.service.CountryService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CountryControllerTest {
  private final CountryService mockCountryService = Mockito.mock(CountryService.class);
  private final CountryController countryController = new CountryController(mockCountryService);

  @Test
  void createCountrySuccessfully() {
    doReturn(Boolean.FALSE).when(mockCountryService).existsCountryWithNameAndLanguage(any(), any());
    doReturn(CountryTestUtil.createCountryResponse(new Long(1), "USA", "en"))
        .when(mockCountryService)
        .save(any(CountryRequest.class));

    CountryResponse countryResponse =
        countryController.createCountry(CountryTestUtil.createCountryRequest("USA", "en"));

    verify(mockCountryService, times(1)).existsCountryWithNameAndLanguage(any(), any());
    verify(mockCountryService, times(1)).save(any());
    assertThat(countryResponse.getId(), is(equalTo(1L)));
    assertThat(countryResponse.getName(), is(equalTo("USA")));
    assertThat(countryResponse.getLanguage(), is(equalTo("en")));
  }

  @Test
  void exceptionIsThrownWhenInsertingDuplicateCountry() {
    doReturn(Boolean.TRUE).when(mockCountryService).existsCountryWithNameAndLanguage(any(), any());

    assertThrows(
        InvalidInputException.class,
        () -> {
          countryController.createCountry(CountryTestUtil.createCountryRequest("USA", "en"));
        });
    verify(mockCountryService, times(1)).existsCountryWithNameAndLanguage(any(), any());
    verify(mockCountryService, times(0)).save(any());
  }

  @Test
  void updateCountrySuccessfully() {
    doReturn(Boolean.TRUE).when(mockCountryService).existsCountryWithId(any());
    doReturn(CountryTestUtil.createCountryResponse(new Long(1), "USA", "en"))
        .when(mockCountryService)
        .update(any(CountryRequest.class), anyLong());

    CountryResponse countryResponse =
        countryController.updateCountry(
            new Long(1), CountryTestUtil.createCountryRequest("USA", "en"));

    verify(mockCountryService, times(1)).existsCountryWithId(any());
    verify(mockCountryService, times(1)).update(any(CountryRequest.class), anyLong());
    assertThat(countryResponse.getId(), is(equalTo(1L)));
    assertThat(countryResponse.getName(), is(equalTo("USA")));
    assertThat(countryResponse.getLanguage(), is(equalTo("en")));
  }

  @Test
  void exceptionIsThrownWhenUpdatingNonExistentCountry() {
    doReturn(Boolean.FALSE).when(mockCountryService).existsCountryWithId(any());

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          countryController.updateCountry(
              new Long(1), CountryTestUtil.createCountryRequest("USA", "en"));
        });
    verify(mockCountryService, times(1)).existsCountryWithId(any());
    verify(mockCountryService, times(0)).update(any(), any());
  }

  @Test
  void exceptionIsThrownWhenUpdatingCountryWithInvalidLanguage() {
    doReturn(Boolean.FALSE).when(mockCountryService).existsCountryWithId(any());

    assertThrows(
        InvalidInputException.class,
        () -> {
          countryController.updateCountry(
              new Long(1), CountryTestUtil.createCountryRequest("Brazil", "br"));
        });
    verify(mockCountryService, times(0)).existsCountryWithId(any());
    verify(mockCountryService, times(0)).update(any(), any());
  }

  @Test
  void deleteCountrySuccessfully() {
    doReturn(Boolean.TRUE).when(mockCountryService).existsCountryWithId(any());
    doReturn(CountryTestUtil.createCountryResponse(new Long(1), "USA", "en"))
        .when(mockCountryService)
        .delete(anyLong());

    CountryResponse countryResponse = countryController.deleteCountry(new Long(1));

    verify(mockCountryService, times(1)).existsCountryWithId(any());
    verify(mockCountryService, times(1)).delete(anyLong());
    assertThat(countryResponse.getId(), is(equalTo(1L)));
    assertThat(countryResponse.getName(), is(equalTo("USA")));
    assertThat(countryResponse.getLanguage(), is(equalTo("en")));
  }

  @Test
  void exceptionIsThrownWhenDeletingNonExistentCountry() {
    doReturn(Boolean.FALSE).when(mockCountryService).existsCountryWithId(any());

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          countryController.updateCountry(
              new Long(1), CountryTestUtil.createCountryRequest("USA", "en"));
        });
    verify(mockCountryService, times(1)).existsCountryWithId(any());
    verify(mockCountryService, times(0)).delete(any());
  }

  @Test
  void findCountryByIdSuccessfully() {
    doReturn(Boolean.TRUE).when(mockCountryService).existsCountryWithId(any());
    doReturn(CountryTestUtil.createCountryResponse(new Long(1), "USA", "en"))
        .when(mockCountryService)
        .findById(anyLong());

    CountryResponse countryResponse = countryController.getCountryById(new Long(1));

    verify(mockCountryService, times(1)).existsCountryWithId(any());
    verify(mockCountryService, times(1)).findById(anyLong());
    assertThat(countryResponse.getId(), is(equalTo(1L)));
    assertThat(countryResponse.getName(), is(equalTo("USA")));
    assertThat(countryResponse.getLanguage(), is(equalTo("en")));
  }

  @Test
  void exceptionIsThrownWhenFindingNonExistentCountry() {
    doReturn(Boolean.FALSE).when(mockCountryService).existsCountryWithId(any());

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          countryController.getCountryById(new Long(1));
        });
    verify(mockCountryService, times(1)).existsCountryWithId(any());
    verify(mockCountryService, times(0)).findById(any());
  }

  @Test
  void findCountriesSuccessfully() {
    doReturn(CountryTestUtil.createCountryResponseList()).when(mockCountryService).findAll();

    List<CountryResponse> countriesResponse = countryController.getCountries();

    verify(mockCountryService, times(1)).findAll();
    assertThat(countriesResponse, is(not(empty())));
    assertThat(countriesResponse.size(), is(equalTo(3)));
  }

  @Test
  void findCountriesSuccessfullyAnEmptyList() {
    doReturn(new ArrayList<>()).when(mockCountryService).findAll();

    List<CountryResponse> countriesResponse = countryController.getCountries();

    verify(mockCountryService, times(1)).findAll();
    assertThat(countriesResponse, is(empty()));
  }
}
