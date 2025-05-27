package org.emp.shelterhub.lib.db.entity;

public class ScheduleEntryEntity {
  public int id;
  public String date;
  public String startTime;
  public String endTime;
  public int employeeId;
  public String taskDescription;

  public ScheduleEntryEntity(
      int id,
      String date,
      String startTime,
      String endTime,
      int employeeId,
      String taskDescription) {
    this.id = id;
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
    this.employeeId = employeeId;
    this.taskDescription = taskDescription;
  }
}
