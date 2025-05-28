package org.emp.shelterhub.lib.infrastructure.mapper;

import java.time.LocalDate;
import java.time.LocalTime;
import org.emp.shelterhub.feature.employee.data.Employee;
import org.emp.shelterhub.feature.scheduler.data.ScheduleEntry;
import org.emp.shelterhub.lib.db.entity.EmployeeEntity;
import org.emp.shelterhub.lib.db.entity.ScheduleEntryEntity;

public class SchedulerMapper {

  public static ScheduleEntry toDomain(ScheduleEntryEntity entity, EmployeeEntity employeeEntity) {
    Employee employee = EmployeeMapper.toDomain(employeeEntity);

    return new ScheduleEntry(
        entity.id,
        LocalDate.parse(entity.date),
        LocalTime.parse(entity.startTime),
        LocalTime.parse(entity.endTime),
        employee,
        entity.taskDescription);
  }

  public static ScheduleEntryEntity toEntity(ScheduleEntry entry) {
    return new ScheduleEntryEntity(
        entry.getId(),
        entry.getDate().toString(),
        entry.getStartTime().toString(),
        entry.getEndTime().toString(),
        entry.getAssignedEmployee().getEmployeeId(),
        entry.getTaskDescription());
  }
}
