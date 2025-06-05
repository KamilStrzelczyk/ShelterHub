package org.emp.shelterhub.lib.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;
import org.emp.shelterhub.feature.employee.data.Employee;
import org.emp.shelterhub.lib.db.dao.EmployeesDAO;
import org.emp.shelterhub.lib.db.entity.EmployeeEntity;
import org.emp.shelterhub.lib.infrastructure.mapper.EmployeeMapper;

public class EmployeeRepository {

  private static final EmployeesDAO employeesDAO = new EmployeesDAO();

  public static boolean createEmployee(Employee employee) {
    EmployeeEntity entity = EmployeeMapper.toEntity(employee);
    boolean success = employeesDAO.addEmployee(entity);

    if (success && entity.id_pracownika != -1) {
      employee.setEmployeeId(entity.id_pracownika);
    }

    return success;
  }

  public static List<Employee> getAllEmployees() {
    List<EmployeeEntity> entityList = employeesDAO.getAllEmployees();
    List<Employee> employeeList = new ArrayList<>();

    for (EmployeeEntity entity : entityList) {
      employeeList.add(EmployeeMapper.toDomain(entity));
    }

    return employeeList;
  }

  public static boolean updateEmployee(Employee employee) {
    EmployeeEntity entity = EmployeeMapper.toEntity(employee);
    return employeesDAO.updateEmployee(entity);
  }

  public static boolean deleteEmployee(int employeeId) {
    return employeesDAO.deleteEmployee(employeeId);
  }
}
