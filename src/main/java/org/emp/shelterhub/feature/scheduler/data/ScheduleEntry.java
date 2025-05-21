package org.emp.shelterhub.feature.scheduler.data;

import java.time.LocalDate;
import java.time.LocalTime;
import org.emp.shelterhub.feature.employee.data.Employee;

public class ScheduleEntry {
  private String id;
  private LocalDate date;
  private LocalTime startTime;
  private LocalTime endTime;
  private Employee assignedEmployee;
  private String taskDescription;

  public ScheduleEntry(
      String id,
      LocalDate date,
      LocalTime startTime,
      LocalTime endTime,
      Employee assignedEmployee,
      String taskDescription) {
    this.id = id;
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
    this.assignedEmployee = assignedEmployee;
    this.taskDescription = taskDescription;
  }

  // Getters
  public String getId() {
    return id;
  }

  public LocalDate getDate() {
    return date;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public LocalTime getEndTime() {
    return endTime;
  }

  public Employee getAssignedEmployee() {
    return assignedEmployee;
  }

  public String getTaskDescription() {
    return taskDescription;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
  }

  public void setAssignedEmployee(Employee assignedEmployee) {
    this.assignedEmployee = assignedEmployee;
  }

  public void setTaskDescription(String taskDescription) {
    this.taskDescription = taskDescription;
  }
}
