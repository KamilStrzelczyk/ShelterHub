package org.emp.shelterhub.feature.settings.data;

public class User {
  private String id;
  private String username;
  private String email;
  private String phoneNumber;
  private UserRole userRole;
  private String passwordHash;

  public User(
      String id,
      String username,
      String email,
      String phoneNumber,
      UserRole userRole,
      String passwordHash) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.userRole = userRole;
    this.passwordHash = passwordHash;
  }

  public String getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public UserRole getUserRole() {
    return userRole;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setUserRole(UserRole userRole) {
    this.userRole = userRole;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }
}
