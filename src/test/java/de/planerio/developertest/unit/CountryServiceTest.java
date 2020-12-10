package de.planerio.developertest.unit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import de.planerio.developertest.controller.commons.country.CountryResponse;
import de.planerio.developertest.controller.commons.exceptions.ResourceNotFoundException;
import de.planerio.developertest.model.entities.Country;
import de.planerio.developertest.model.repository.CountryRepository;
import de.planerio.developertest.service.CountryService;
import de.planerio.developertest.service.converters.CountryConverter;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CountryServiceTest {
  private final CountryRepository mockCountryRepository = Mockito.mock(CountryRepository.class);
  private final CountryConverter countryConverter = new CountryConverter();
  private final CountryService service =
      new CountryService(mockCountryRepository, countryConverter);

  @Test
  void givenFindAllWhenValidReturnListOfCountries() {
    // setup
    doReturn(CountryTestUtil.createCountryResponseList()).when(mockCountryRepository).findAll();

    // when
    final List<CountryResponse> result = service.findAll();

    // then
    assertThat("Result list is empty", result, is(not(empty())));
    assertThat("Result list size does not match", result, hasSize(3));
  }

  @Test
  void givenValidIdWhenFindByIdThenReturnCountry() {
    // setup
    doReturn(CountryTestUtil.createCountry(1L, "USA", "en"))
        .when(mockCountryRepository)
        .findCountryById(anyLong());

    // when
    CountryResponse result = service.findById(1L);

    // then
    assertThat(result.getId(), is(equalTo(1L)));
    assertThat(result.getName(), is(equalTo("USA")));
    assertThat(result.getLanguage(), is(equalTo("en")));
  }

  @Test
  void givenValidCountryWhenSaveThenSucceed() {
    // setup
    doReturn(CountryTestUtil.createCountry(1L, "USA", "en"))
        .when(mockCountryRepository)
        .save(any(Country.class));

    // when
    final CountryResponse result = service.save(CountryTestUtil.createCountryRequest("USA", "en"));

    // then
    assertThat(result.getId(), is(equalTo(1L)));
    assertThat(result.getName(), is(equalTo("USA")));
    assertThat(result.getLanguage(), is(equalTo("en")));
  }

  @Test
  void givenValidCountryWhenUpdateThenSucceed() {
    // setup
    doReturn(Optional.of(CountryTestUtil.createCountry(1L, "USA", "en")))
        .when(mockCountryRepository)
        .findById(anyLong());
    doReturn(CountryTestUtil.createCountry(1L, "United States", "de"))
        .when(mockCountryRepository)
        .save(any(Country.class));

    // when
    final CountryResponse result =
        service.update(CountryTestUtil.createCountryRequest("United States", "de"), 1L);

    // then
    assertThat(result.getId(), is(equalTo(1L)));
    assertThat(result.getName(), is(equalTo("United States")));
    assertThat(result.getLanguage(), is(equalTo("de")));
  }

  @Test
  void updateThrowsExceptionWhenObjectNotFound() {
    // setup
    doReturn(Optional.empty()).when(mockCountryRepository).findById(anyLong());

    // when
    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          service.update(CountryTestUtil.createCountryRequest("United States", "de"), 1L);
        });

    // then
    verify(mockCountryRepository, times(1)).findById(anyLong());
    verify(mockCountryRepository, times(0)).save(any());
    verifyNoMoreInteractions(mockCountryRepository);
  }

  @Test
  void givenValidCountryIdWhenDeleteThenSucceed() {
    // setup
    doReturn(CountryTestUtil.createCountry(1L, "USA", "en"))
        .when(mockCountryRepository)
        .findCountryById(anyLong());

    // when
    CountryResponse countryResponse = service.delete(1L);

    // then
    verify(mockCountryRepository, times(1)).findCountryById(anyLong());
    verify(mockCountryRepository, times(1)).deleteById(any(Long.class));
    verifyNoMoreInteractions(mockCountryRepository);
    assertThat(countryResponse.getId(), is(equalTo(1L)));
    assertThat(countryResponse.getName(), is(equalTo("USA")));
    assertThat(countryResponse.getLanguage(), is(equalTo("en")));
  }

  @Test
  void deleteThrowsExceptionWhenObjectNotFound() {
    // setup
    doReturn(null).when(mockCountryRepository).findCountryById(anyLong());

    // when
    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          service.delete(1L);
        });

    // then
    verify(mockCountryRepository, times(1)).findCountryById(anyLong());
    verify(mockCountryRepository, times(0)).deleteById(any(Long.class));
    verifyNoMoreInteractions(mockCountryRepository);
  }

  @Test
  void existCountryByIdReturnsTrue() {
    doReturn(Optional.of(Boolean.TRUE)).when(mockCountryRepository).existsCountryById(any());
    Boolean exists = service.existsCountryWithId(new Long(1));
    assertThat(exists, is(Boolean.TRUE));
  }

  @Test
  void existCountryByIdReturnsFalse() {
    doReturn(Optional.empty()).when(mockCountryRepository).existsCountryById(any());
    Boolean exists = service.existsCountryWithId(new Long(1));
    assertThat(exists, is(Boolean.FALSE));
  }

  @Test
  void existCountryByNameAndLanguageReturnsTrue() {
    doReturn(Optional.of(Boolean.TRUE))
        .when(mockCountryRepository)
        .existsCountryByNameAndLanguage(any(), any());
    Boolean exists = service.existsCountryWithNameAndLanguage("name", "en");
    assertThat(exists, is(Boolean.TRUE));
  }

  @Test
  void existCountryByNameAndLanguageReturnsFalse() {
    doReturn(Optional.empty())
        .when(mockCountryRepository)
        .existsCountryByNameAndLanguage(any(), any());
    Boolean exists = service.existsCountryWithNameAndLanguage("name", "en");
    assertThat(exists, is(Boolean.FALSE));
  }
}
