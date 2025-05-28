package org.emp.shelterhub.feature.settings.data;

public class User {
  private int id;
  private String username;
  private String email;
  private String phoneNumber;
  private UserRole userRole;
  private String password;

  public User(
      int id,
      String username,
      String email,
      String phoneNumber,
      UserRole userRole,
      String password) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.userRole = userRole;
    this.password = password;
  }

  public int getId() {
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

  public String getPassword() {
    return password;
  }

  public void setId(int id) {
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

  public void setPassword(String password) {
    this.password = password;
  }
}
