package org.emp.shelterhub.lib.db.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.emp.shelterhub.lib.db.database.SHDataBase;
import org.emp.shelterhub.lib.db.entity.EmployeeEntity;

public class EmployeesDAO {

  public List<EmployeeEntity> getAllEmployees() {
    List<EmployeeEntity> list = new ArrayList<>();
    String query = "SELECT * FROM pracownicy";

    try (Connection conn = SHDataBase.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)) {

      while (rs.next()) {
        list.add(
            new EmployeeEntity(
                rs.getInt("id_pracownika"),
                rs.getString("imie1"),
                rs.getString("imie2"),
                rs.getString("nazwisko"),
                rs.getString("data_ur"),
                rs.getString("adres"),
                rs.getString("telefon")));
      }

    } catch (SQLException e) {
      System.out.println("Błąd pobierania pracowników: " + e.getMessage());
    }

    return list;
  }

  public EmployeeEntity getById(int id) {
    String query = "SELECT * FROM pracownicy WHERE id_pracownika = ?";
    try (Connection conn = SHDataBase.connect();
        PreparedStatement ps = conn.prepareStatement(query)) {

      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return new EmployeeEntity(
              rs.getInt("id_pracownika"),
              rs.getString("imie1"),
              rs.getString("imie2"),
              rs.getString("nazwisko"),
              rs.getString("data_ur"),
              rs.getString("adres"),
              rs.getString("telefon"));
        }
      }
    } catch (SQLException e) {
      System.out.println("Błąd pobierania pracownika po ID: " + e.getMessage());
    }
    return null;
  }

  public boolean addEmployee(EmployeeEntity emp) {
    String sql =
        "INSERT INTO pracownicy (imie1, imie2, nazwisko, data_ur, adres, telefon) VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection conn = SHDataBase.connect();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      ps.setString(1, emp.imie1);
      ps.setString(2, emp.imie2);
      ps.setString(3, emp.nazwisko);
      ps.setString(4, emp.data_ur);
      ps.setString(5, emp.adres);
      ps.setString(6, emp.telefon);

      int affectedRows = ps.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Creating employee failed, no rows affected.");
      }

      try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          emp.id_pracownika = generatedKeys.getInt(1);
        } else {
          throw new SQLException("Creating employee failed, no ID obtained.");
        }
      }

      return true;

    } catch (SQLException e) {
      System.out.println("Błąd dodawania pracownika: " + e.getMessage());
      return false;
    }
  }

  public boolean updateEmployee(EmployeeEntity emp) {
    String sql =
        "UPDATE pracownicy SET imie1 = ?, imie2 = ?, nazwisko = ?, data_ur = ?, adres = ?, telefon = ? WHERE id_pracownika = ?";

    try (Connection conn = SHDataBase.connect();
        PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setString(1, emp.imie1);
      ps.setString(2, emp.imie2);
      ps.setString(3, emp.nazwisko);
      ps.setString(4, emp.data_ur);
      ps.setString(5, emp.adres);
      ps.setString(6, emp.telefon);
      ps.setInt(7, emp.id_pracownika);
      ps.executeUpdate();
      return true;

    } catch (SQLException e) {
      System.out.println("Błąd aktualizacji pracownika: " + e.getMessage());
      return false;
    }
  }

  public boolean deleteEmployee(int id_pracownika) {
    String sql = "DELETE FROM pracownicy WHERE id_pracownika = ?";

    try (Connection conn = SHDataBase.connect();
        PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, id_pracownika);
      ps.executeUpdate();
      return true;

    } catch (SQLException e) {
      System.out.println("Błąd usuwania pracownika: " + e.getMessage());
      return false;
    }
  }
}
