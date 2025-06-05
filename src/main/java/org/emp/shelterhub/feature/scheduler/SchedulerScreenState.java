package org.emp.shelterhub.feature.scheduler;

import java.util.ArrayList;
import java.util.List;
import org.emp.shelterhub.feature.employee.data.Employee;
import org.emp.shelterhub.feature.scheduler.data.ScheduleEntry;

public class SchedulerScreenState {

  private final List<Employee> employees;
  private final List<ScheduleEntry> scheduleEntries;
  private final boolean isLoading;
  private final String errorMessage;

  public SchedulerScreenState(
      List<Employee> employees,
      List<ScheduleEntry> scheduleEntries,
      boolean isLoading,
      String errorMessage) {
    this.employees = employees;
    this.scheduleEntries = scheduleEntries;
    this.isLoading = isLoading;
    this.errorMessage = errorMessage;
  }

  public static SchedulerScreenState initialState() {
    return new SchedulerScreenState(new ArrayList<>(), new ArrayList<>(), false, null);
  }

  public SchedulerScreenState withEmployees(List<Employee> employees) {
    return new SchedulerScreenState(
        employees, this.scheduleEntries, this.isLoading, this.errorMessage);
  }

  public SchedulerScreenState withScheduleEntries(List<ScheduleEntry> scheduleEntries) {
    return new SchedulerScreenState(
        this.employees, scheduleEntries, this.isLoading, this.errorMessage);
  }

  public SchedulerScreenState withLoading(boolean isLoading) {
    return new SchedulerScreenState(
        this.employees, this.scheduleEntries, isLoading, this.errorMessage);
  }

  public SchedulerScreenState withErrorMessage(String errorMessage) {
    return new SchedulerScreenState(
        this.employees, this.scheduleEntries, this.isLoading, errorMessage);
  }

  public List<Employee> getEmployees() {
    return employees;
  }

  public List<ScheduleEntry> getScheduleEntries() {
    return scheduleEntries;
  }

  public boolean isLoading() {
    return isLoading;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
