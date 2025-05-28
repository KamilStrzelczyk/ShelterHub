package org.emp.shelterhub.feature.settings.data;

public enum UserRole {
  ADMINISTRATOR("Administrator"),
  STANDARD_USER("Użytkownik standardowy"),
  GUEST("Gość");

  private final String displayName;

  UserRole(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
