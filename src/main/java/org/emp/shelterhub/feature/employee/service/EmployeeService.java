package org.emp.shelterhub.feature.employee.service;

import org.emp.shelterhub.feature.employee.data.Employee;
import org.emp.shelterhub.lib.db.Base;
import org.emp.shelterhub.lib.db.EmployeesDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    public static boolean createEmployee(Employee employee) {
        String sql = "INSERT INTO pracownicy (imie1, imie2, nazwisko, data_ur, adres, telefon) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Base.connect();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getMiddleName());
            ps.setString(3, employee.getLastName());
            ps.setString(4, employee.getDateOfBirth());
            ps.setString(5, employee.getAddress());
            ps.setString(6, employee.getPhoneNumber());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        employee.setEmployeeId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }

            return false;
        } catch (SQLException e) {
            System.out.println("Error creating employee: " + e.getMessage());
            return false;
        }
    }

    public static List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        EmployeesDAO employeesDAO = new EmployeesDAO();
        List<EmployeesDAO.Employee> daoEmployees = employeesDAO.getAllEmployees();

        for (EmployeesDAO.Employee daoEmployee : daoEmployees) {
            Employee employee = new Employee(
                daoEmployee.id_pracownika,
                daoEmployee.imie1,
                daoEmployee.imie2,
                daoEmployee.nazwisko,
                daoEmployee.data_ur,
                daoEmployee.adres,
                daoEmployee.telefon
            );
            employees.add(employee);
        }

        return employees;
    }

    public static boolean updateEmployee(Employee employee) {
        EmployeesDAO employeesDAO = new EmployeesDAO();
        EmployeesDAO.Employee daoEmployee = new EmployeesDAO.Employee(
            employee.getFirstName(),
            employee.getMiddleName(),
            employee.getLastName(),
            employee.getDateOfBirth(),
            employee.getAddress(),
            employee.getPhoneNumber()
        );
        daoEmployee.id_pracownika = employee.getEmployeeId();

        return employeesDAO.updateEmployee(daoEmployee);
    }

    public static boolean deleteEmployee(int employeeId) {
        EmployeesDAO employeesDAO = new EmployeesDAO();
        return employeesDAO.deleteEmployee(employeeId);
    }
}
