package org.emp.shelterhub.lib.db.entity;

public class UserEntity {
  public Integer id;
  public String username;
  public String password;
  public String role;
  public Integer employeeId;

  public UserEntity(Integer id, String username, String password, String role, Integer employeeId) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.role = role;
    this.employeeId = employeeId;
  }

  public UserEntity(String username, String password, String role, Integer employeeId) {
    this(null, username, password, role, employeeId);
  }
}
