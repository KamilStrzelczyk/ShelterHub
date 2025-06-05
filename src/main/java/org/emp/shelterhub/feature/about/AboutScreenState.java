package org.emp.shelterhub.feature.about;

public class AboutScreenState {
  private final String username;
  private final String role;
  private final String version;

  private AboutScreenState(String username, String role, String version) {
    this.username = username;
    this.role = role;
    this.version = version;
  }

  public static AboutScreenState initial(String version) {
    return new AboutScreenState("", "", version);
  }

  public String getUsername() {
    return username;
  }

  public String getRole() {
    return role;
  }

  public String getVersion() {
    return version;
  }

  public AboutScreenState withUsernameAndRole(String username, String role) {
    return new AboutScreenState(username, role, this.version);
  }
}
