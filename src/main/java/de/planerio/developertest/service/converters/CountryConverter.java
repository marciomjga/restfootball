package de.planerio.developertest.service.converters;

import de.planerio.developertest.controller.commons.country.CountryResponse;
import de.planerio.developertest.model.entities.Country;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class CountryConverter extends ConfigurableMapper {

  @Override
  protected void configure(MapperFactory factory) {
    factory.classMap(CountryResponse.class, Country.class).byDefault().register();
  }
}
