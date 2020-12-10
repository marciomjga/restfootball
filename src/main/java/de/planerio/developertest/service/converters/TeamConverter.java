package de.planerio.developertest.service.converters;

import de.planerio.developertest.controller.commons.team.TeamResponse;
import de.planerio.developertest.model.entities.Team;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class TeamConverter extends ConfigurableMapper {

  @Override
  protected void configure(MapperFactory factory) {
    factory.classMap(TeamResponse.class, Team.class).byDefault().register();
  }
}
