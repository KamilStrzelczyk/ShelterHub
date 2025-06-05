package org.emp.shelterhub.lib.db.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.emp.shelterhub.lib.db.database.SHDataBase;
import org.emp.shelterhub.lib.db.entity.RoomEntity;

public class RoomsDAO {

  public List<RoomEntity> getRooms() {
    String query = "SELECT * FROM pokoje";
    return executeSelectQuery(query);
  }

  public List<RoomEntity> getAvailableRooms() {
    String query = "SELECT * FROM pokoje WHERE dostepnosc = 'dostepny'";
    return executeSelectQuery(query);
  }

  public boolean addRoom(RoomEntity room) {
    String query = "INSERT INTO pokoje (stan, typ, cena_za_noc) VALUES (?, ?, ?)";

    try (Connection conn = SHDataBase.connect();
        PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setString(1, room.stan);
      ps.setString(2, room.typ);
      ps.setDouble(3, room.cena);
      ps.executeUpdate();
      return true;
    } catch (SQLException e) {
      System.out.println("Błąd dodawania pokoju: " + e.getMessage());
      return false;
    }
  }

  public boolean updateRoom(RoomEntity room) {
    String sql = "UPDATE pokoje SET stan = ?, typ = ?, cena_za_noc = ? WHERE kod_pokoj = ?";

    try (Connection conn = SHDataBase.connect();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, room.stan);
      ps.setString(2, room.typ);
      ps.setDouble(3, room.cena);
      ps.setInt(4, room.kod_pokoj);
      ps.executeUpdate();
      return true;

    } catch (SQLException e) {
      System.out.println("Błąd aktualizacji pokoju: " + e.getMessage());
      return false;
    }
  }

  public boolean deleteRoom(int kod_pokoj) {
    String sql = "DELETE FROM pokoje WHERE kod_pokoj = ?";

    try (Connection conn = SHDataBase.connect();
        PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, kod_pokoj);
      ps.executeUpdate();
      return true;

    } catch (SQLException e) {
      System.out.println("Błąd usuwania pokoju: " + e.getMessage());
      return false;
    }
  }

  private List<RoomEntity> executeSelectQuery(String query) {
    List<RoomEntity> rooms = new ArrayList<>();

    try (Connection conn = SHDataBase.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)) {

      while (rs.next()) {
        rooms.add(
            new RoomEntity(
                rs.getInt("kod_pokoj"),
                rs.getString("stan"),
                rs.getString("typ"),
                rs.getDouble("cena_za_noc")));
      }

    } catch (SQLException e) {
      System.out.println("Błąd SELECT: " + e.getMessage());
    }

    return rooms;
  }
}
