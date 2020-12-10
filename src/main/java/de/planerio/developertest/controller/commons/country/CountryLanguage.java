package de.planerio.developertest.controller.commons.country;

public enum CountryLanguage {
  DE("de"),
  FR("fr"),
  EN("en"),
  ES("es"),
  IT("it");

  private String displayText;

  CountryLanguage(String displayText) {
    this.displayText = displayText;
  }

  public String getDisplayText() {
    return displayText;
  }

  @Override
  public String toString() {
    return "CountryLanguage{" + "displayText='" + displayText + '\'' + '}';
  }
}
