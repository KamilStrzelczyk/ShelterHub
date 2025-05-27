package org.emp.shelterhub.feature.employee;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.emp.shelterhub.feature.employee.data.Employee;

public class EmployeeScreenState {
  private final List<Employee> employees;
  private final boolean isLoading;
  private final String errorMessage;

  private EmployeeScreenState(List<Employee> employees, boolean isLoading, String errorMessage) {
    this.employees = Collections.unmodifiableList(employees);
    this.isLoading = isLoading;
    this.errorMessage = errorMessage;
  }

  public static EmployeeScreenState initialState() {
    return new EmployeeScreenState(Collections.emptyList(), false, null);
  }

  public EmployeeScreenState withEmployees(List<Employee> employees) {
    return new EmployeeScreenState(employees, this.isLoading, this.errorMessage);
  }

  public EmployeeScreenState withLoading(boolean isLoading) {
    return new EmployeeScreenState(this.employees, isLoading, this.errorMessage);
  }

  public EmployeeScreenState withErrorMessage(String errorMessage) {
    return new EmployeeScreenState(this.employees, this.isLoading, errorMessage);
  }

  public List<Employee> getEmployees() {
    return employees;
  }

  public boolean isLoading() {
    return isLoading;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EmployeeScreenState that = (EmployeeScreenState) o;
    return isLoading == that.isLoading
        && Objects.equals(employees, that.employees)
        && Objects.equals(errorMessage, that.errorMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(employees, isLoading, errorMessage);
  }

  @Override
  public String toString() {
    return "EmployeeScreenState{"
        + "employees="
        + employees.size()
        + ", isLoading="
        + isLoading
        + ", errorMessage='"
        + errorMessage
        + '\''
        + '}';
  }
}
