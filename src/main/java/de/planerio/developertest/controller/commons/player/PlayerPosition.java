package de.planerio.developertest.controller.commons.player;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Arrays;
import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum PlayerPosition {
  GK,
  CB,
  RB,
  LB,
  LWB,
  RWB,
  CDM,
  CM,
  LM,
  RM,
  CAM,
  ST,
  CF;

  public static List<String> getDefensivePositions() {
    return Arrays.asList(GK.name(), CB.name(), RB.name(), LB.name(), LWB.name(), RWB.name());
  }
}
