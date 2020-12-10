package de.planerio.developertest.service.converters;

import de.planerio.developertest.controller.commons.player.PlayerResponse;
import de.planerio.developertest.model.entities.Player;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class PlayerConverter extends ConfigurableMapper {

  @Override
  protected void configure(MapperFactory factory) {
    factory.classMap(PlayerResponse.class, Player.class).byDefault().register();
  }
}
