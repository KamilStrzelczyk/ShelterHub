package org.emp.shelterhub.lib.infrastructure.mapper;

import org.emp.shelterhub.feature.employee.data.Employee;
import org.emp.shelterhub.lib.db.entity.EmployeeEntity;

public class EmployeeMapper {

  public static Employee toDomain(EmployeeEntity entity) {
    return new Employee(
        entity.id_pracownika,
        entity.imie1,
        entity.imie2,
        entity.nazwisko,
        entity.data_ur,
        entity.adres,
        entity.telefon);
  }

  public static EmployeeEntity toEntity(Employee employee) {
    EmployeeEntity entity =
        new EmployeeEntity(
            employee.getFirstName(),
            employee.getMiddleName(),
            employee.getLastName(),
            employee.getDateOfBirth(),
            employee.getAddress(),
            employee.getPhoneNumber());
    entity.id_pracownika = employee.getEmployeeId();
    return entity;
  }
}
