package org.emp.shelterhub.lib.db.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.emp.shelterhub.lib.db.database.SHDataBase;
import org.emp.shelterhub.lib.db.entity.UserEntity;

public class UserDAO {

  public List<UserEntity> getAllUsers() {
    String query = "SELECT * FROM uzytkownicy";
    List<UserEntity> users = new ArrayList<>();

    try (Connection conn = SHDataBase.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)) {

      while (rs.next()) {
        users.add(
            new UserEntity(
                rs.getInt("id_uzytkownika"),
                rs.getString("login"),
                rs.getString("haslo"),
                rs.getString("rola"),
                rs.getInt("id_pracownika")));
      }
    } catch (SQLException e) {
      System.out.println("Błąd SELECT uzytkownicy: " + e.getMessage());
    }

    return users;
  }

  public boolean addUser(UserEntity user) {
    String query =
        "INSERT INTO uzytkownicy (login, haslo, rola, id_pracownika) VALUES (?, ?, ?, ?)";

    try (Connection conn = SHDataBase.connect();
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

      ps.setString(1, user.username);
      ps.setString(2, user.password);
      ps.setString(3, user.role);
      if (user.employeeId != null) {
        ps.setInt(4, user.employeeId);
      } else {
        ps.setNull(4, Types.INTEGER);
      }

      int affectedRows = ps.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Dodanie użytkownika nie powiodło się, brak wierszy.");
      }

      try (ResultSet keys = ps.getGeneratedKeys()) {
        if (keys.next()) {
          user.id = keys.getInt(1);
        }
      }

      return true;
    } catch (SQLException e) {
      System.out.println("Błąd dodawania użytkownika: " + e.getMessage());
      return false;
    }
  }

  public boolean deleteUserById(int id) {
    String query = "DELETE FROM uzytkownicy WHERE id_uzytkownika = ?";

    try (Connection conn = SHDataBase.connect();
        PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setInt(1, id);
      ps.executeUpdate();
      return true;
    } catch (SQLException e) {
      System.out.println("Błąd usuwania użytkownika: " + e.getMessage());
      return false;
    }
  }

  public UserEntity getUserByLogin(String login) {
    String query = "SELECT * FROM uzytkownicy WHERE login = ?";

    try (Connection conn = SHDataBase.connect();
        PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setString(1, login);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return new UserEntity(
              rs.getInt("id_uzytkownika"),
              rs.getString("login"),
              rs.getString("haslo"),
              rs.getString("rola"),
              rs.getInt("id_pracownika"));
        }
      }
    } catch (SQLException e) {
      System.out.println("Błąd pobierania użytkownika po loginie: " + e.getMessage());
    }

    return null;
  }
}
