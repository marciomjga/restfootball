package de.planerio.developertest.controller.commons.exceptions;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResourceNotFoundException extends RuntimeException {

  String resourceName;
  String resourceId;

  /**
   * Constructs a new runtime exception with {@code null} as its detail message. The cause is not
   * initialized, and may subsequently be initialized by a call to {@link #initCause}.
   */
  public ResourceNotFoundException(String message) {
    super(message);
  }

  public ResourceNotFoundException(String resourceName, String resourceId) {
    super(String.format("Resource '%s' not found with id '%s'", resourceName, resourceId));
    this.resourceName = resourceName;
    this.resourceId = resourceId;
  }
}
