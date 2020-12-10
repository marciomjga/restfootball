package de.planerio.developertest.service.converters;

import de.planerio.developertest.controller.commons.league.LeagueResponse;
import de.planerio.developertest.model.entities.League;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class LeagueConverter extends ConfigurableMapper {

  @Override
  protected void configure(MapperFactory factory) {
    factory.classMap(LeagueResponse.class, League.class).byDefault().register();
  }
}
