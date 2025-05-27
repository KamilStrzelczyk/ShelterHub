package org.emp.shelterhub.lib.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.emp.shelterhub.feature.scheduler.data.ScheduleEntry;
import org.emp.shelterhub.lib.db.dao.EmployeesDAO;
import org.emp.shelterhub.lib.db.dao.SchedulerDAO;
import org.emp.shelterhub.lib.db.entity.EmployeeEntity;
import org.emp.shelterhub.lib.db.entity.ScheduleEntryEntity;
import org.emp.shelterhub.lib.infrastructure.mapper.SchedulerMapper;

public class SchedulerRepository {

  private static final SchedulerDAO schedulerDAO = new SchedulerDAO();
  private static final EmployeesDAO employeeDAO = new EmployeesDAO();

  public List<ScheduleEntry> getAllEntries() {
    return schedulerDAO.getAllEntries().stream()
        .map(
            entity -> {
              EmployeeEntity emp = employeeDAO.getById(entity.employeeId);
              return SchedulerMapper.toDomain(entity, emp);
            })
        .collect(Collectors.toList());
  }

  public boolean addEntry(ScheduleEntry entry) {
    ScheduleEntryEntity entity = SchedulerMapper.toEntity(entry);
    return schedulerDAO.addEntry(entity);
  }

  public boolean updateEntry(ScheduleEntry entry) {
    ScheduleEntryEntity entity = SchedulerMapper.toEntity(entry);
    return schedulerDAO.updateEntry(entity);
  }

  public boolean deleteEntry(String id) {
    return schedulerDAO.deleteEntry(id);
  }

  public Optional<ScheduleEntry> getEntryById(int id) {
    return schedulerDAO.getAllEntries().stream()
        .filter(e -> e.id == id)
        .findFirst()
        .map(
            entity -> {
              EmployeeEntity emp = employeeDAO.getById(entity.employeeId);
              return SchedulerMapper.toDomain(entity, emp);
            });
  }
}
