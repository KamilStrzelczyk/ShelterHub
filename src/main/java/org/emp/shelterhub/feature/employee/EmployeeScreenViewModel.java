package org.emp.shelterhub.feature.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.emp.shelterhub.feature.employee.data.Employee;
import org.emp.shelterhub.feature.employee.service.EmployeeService;
import org.emp.shelterhub.lib.db.EmployeesDAO;

public class EmployeeScreenViewModel {

  private List<Employee> employees;
  private int nextEmployeeId = 1;

  public EmployeeScreenViewModel() {
    loadEmployeesFromDatabase();
  }

  private void loadEmployeesFromDatabase() {
    try {
      employees = EmployeeService.getAllEmployees();

      if (!employees.isEmpty()) {
        int maxId = employees.stream()
            .mapToInt(Employee::getEmployeeId)
            .max()
            .orElse(0);
        nextEmployeeId = maxId + 1;
      }

      System.out.println("Loaded " + employees.size() + " employees from database");
    } catch (Exception e) {
      System.out.println("Error loading employees from database: " + e.getMessage());
      employees = new ArrayList<>();
    }
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

    boolean success = EmployeeService.createEmployee(newEmployee);

    if (success) {
      this.employees.add(newEmployee);
      System.out.println(
          "Added new employee to database: " + newEmployee.getFirstName() + " " + newEmployee.getLastName());
    } else {
      System.out.println(
          "Failed to add employee to database: " + newEmployee.getFirstName() + " " + newEmployee.getLastName());
      nextEmployeeId--;
    }
  }

  public void updateEmployee(Employee updatedEmployee) {
    boolean success = EmployeeService.updateEmployee(updatedEmployee);

    if (success) {
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
            System.out.println("Updated employee in database: " + e.getFirstName() + " " + e.getLastName());
          });
    } else {
      System.out.println(
          "Failed to update employee in database: " + updatedEmployee.getFirstName() + " " + updatedEmployee.getLastName());
    }
  }

  public boolean deleteEmployee(Employee employee) {
    boolean success = EmployeeService.deleteEmployee(employee.getEmployeeId());

    if (success) {
      employees.removeIf(e -> e.getEmployeeId() == employee.getEmployeeId());
      System.out.println("Deleted employee from database: " + employee.getFirstName() + " " + employee.getLastName());
    } else {
      System.out.println("Failed to delete employee from database: " + employee.getFirstName() + " " + employee.getLastName());
    }

    return success;
  }
}
