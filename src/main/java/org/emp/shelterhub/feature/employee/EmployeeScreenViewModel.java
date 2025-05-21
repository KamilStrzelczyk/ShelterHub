package org.emp.shelterhub.feature.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.emp.shelterhub.feature.employee.data.Employee;

public class EmployeeScreenViewModel {

  private List<Employee> employees;
  private int nextEmployeeId = 6;

  public EmployeeScreenViewModel() {
    employees = new ArrayList<>();
    employees.add(
        new Employee(
            1, "Jan", "", "Kowalski", "1990-01-15", "ul. Długa 1, Warszawa", "123-456-789"));
    employees.add(
        new Employee(
            2, "Anna", "Maria", "Nowak", "1985-03-20", "ul. Krótka 5, Kraków", "987-654-321"));
    employees.add(
        new Employee(
            3,
            "Piotr",
            "",
            "Wiśniewski",
            "1992-07-01",
            "al. Jerozolimskie 10, Wrocław",
            "555-111-222"));
    employees.add(
        new Employee(
            4, "Katarzyna", "", "Wójcik", "1988-11-10", "ul. Leśna 22, Gdańsk", "333-444-555"));
    employees.add(
        new Employee(
            5, "Tomasz", "Adam", "Kowalczyk", "1995-02-28", "ul. Polna 7, Poznań", "777-888-999"));
  }

  public List<Employee> getEmployees() {
    return employees;
  }

  public void handleEmployeeClick(Employee employee) {
    System.out.println(
        "Employee clicked: " + employee.getFirstName() + " " + employee.getLastName());
  }

  public void addNewEmployee(Employee newEmployee) {
    newEmployee.setEmployeeId(nextEmployeeId++);
    this.employees.add(newEmployee);
    System.out.println(
        "Added new employee: " + newEmployee.getFirstName() + " " + newEmployee.getLastName());
  }

  public void updateEmployee(Employee updatedEmployee) {
    Optional<Employee> existingEmployee =
        employees.stream()
            .filter(e -> e.getEmployeeId() == updatedEmployee.getEmployeeId())
            .findFirst();

    existingEmployee.ifPresent(
        e -> {
          e.setFirstName(updatedEmployee.getFirstName());
          e.setMiddleName(updatedEmployee.getMiddleName());
          e.setLastName(updatedEmployee.getLastName());
          e.setDateOfBirth(updatedEmployee.getDateOfBirth());
          e.setAddress(updatedEmployee.getAddress());
          e.setPhoneNumber(updatedEmployee.getPhoneNumber());
          System.out.println("Updated employee: " + e.getFirstName() + " " + e.getLastName());
        });
  }
}
