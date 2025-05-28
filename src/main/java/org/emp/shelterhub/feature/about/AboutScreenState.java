package org.emp.shelterhub.feature.about;

public class AboutScreenState {
  private final String username;
  private final String role;

  private AboutScreenState(String username, String role) {
    this.username = username;
    this.role = role;
  }

  public static AboutScreenState initial() {
    return new AboutScreenState("", "");
  }

  public String getUsername() {
    return username;
  }

  public String getRole() {
    return role;
  }

  public AboutScreenState withUsernameAndRole(String username, String role) {
    return new AboutScreenState(username, role);
  }
}
