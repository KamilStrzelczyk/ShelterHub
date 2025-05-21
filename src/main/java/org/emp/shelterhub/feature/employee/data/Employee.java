package org.emp.shelterhub.feature.employee.data;

public class Employee {
  private int employeeId;
  private String firstName;
  private String middleName;
  private String lastName;
  private String dateOfBirth;
  private String address;
  private String phoneNumber;

  public Employee(
      int employeeId,
      String firstName,
      String middleName,
      String lastName,
      String dateOfBirth,
      String address,
      String phoneNumber) {
    this.employeeId = employeeId;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.address = address;
    this.phoneNumber = phoneNumber;
  }

  // Getters
  public int getEmployeeId() {
    return employeeId;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public String getAddress() {
    return address;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setEmployeeId(int employeeId) {
    this.employeeId = employeeId;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
