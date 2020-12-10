package de.planerio.developertest.controller.commons.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum SortingOrder {
  ASC,
  DESC
}
