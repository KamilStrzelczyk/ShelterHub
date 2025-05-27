package org.emp.shelterhub.lib.db.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.emp.shelterhub.lib.db.database.SHDataBase;
import org.emp.shelterhub.lib.db.entity.ScheduleEntryEntity;

public class SchedulerDAO {

  public List<ScheduleEntryEntity> getAllEntries() {
    String query = "SELECT * FROM scheduler";
    return executeSelectQuery(query);
  }

  public boolean addEntry(ScheduleEntryEntity entry) {
    String query =
        "INSERT INTO scheduler (date, start_time, end_time, id_pracownika, task_description) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = SHDataBase.connect();
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

      ps.setString(1, entry.date);
      ps.setString(2, entry.startTime);
      ps.setString(3, entry.endTime);
      ps.setInt(4, entry.employeeId);
      ps.setString(5, entry.taskDescription);
      int affectedRows = ps.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Dodanie wpisu nie powiodło się, brak wierszy do aktualizacji.");
      }

      try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          entry.id = generatedKeys.getInt(1); // ustaw id w encji
        } else {
          throw new SQLException("Dodanie wpisu nie powiodło się, brak wygenerowanego id.");
        }
      }

      return true;
    } catch (SQLException e) {
      System.out.println("Błąd dodawania wpisu: " + e.getMessage());
      return false;
    }
  }

  public boolean updateEntry(ScheduleEntryEntity entry) {
    String query =
        "UPDATE scheduler SET date = ?, start_time = ?, end_time = ?, id_pracownika = ?, task_description = ? WHERE id = ?";

    try (Connection conn = SHDataBase.connect();
        PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setString(1, entry.date);
      ps.setString(2, entry.startTime);
      ps.setString(3, entry.endTime);
      ps.setInt(4, entry.employeeId);
      ps.setString(5, entry.taskDescription);
      ps.setInt(6, entry.id);
      ps.executeUpdate();
      return true;
    } catch (SQLException e) {
      System.out.println("Błąd aktualizacji wpisu: " + e.getMessage());
      return false;
    }
  }

  public boolean deleteEntry(String id) {
    String query = "DELETE FROM scheduler WHERE id = ?";

    try (Connection conn = SHDataBase.connect();
        PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setString(1, id);
      ps.executeUpdate();
      return true;
    } catch (SQLException e) {
      System.out.println("Błąd usuwania wpisu: " + e.getMessage());
      return false;
    }
  }

  private List<ScheduleEntryEntity> executeSelectQuery(String query) {
    List<ScheduleEntryEntity> entries = new ArrayList<>();

    try (Connection conn = SHDataBase.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)) {

      while (rs.next()) {
        entries.add(
            new ScheduleEntryEntity(
                rs.getInt("id"),
                rs.getString("date"),
                rs.getString("start_time"),
                rs.getString("end_time"),
                rs.getInt("id_pracownika"),
                rs.getString("task_description")));
      }

    } catch (SQLException e) {
      System.out.println("Błąd SELECT scheduler: " + e.getMessage());
    }

    return entries;
  }
}
