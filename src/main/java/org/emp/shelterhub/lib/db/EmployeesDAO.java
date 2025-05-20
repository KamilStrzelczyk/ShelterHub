package org.emp.shelterhub.lib.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeesDAO {

    public static class Employee {
        public int id_pracownika;
        public String imie1, imie2, nazwisko, data_ur, adres, telefon;

        public Employee(int id_pracownika, String imie1, String imie2, String nazwisko, String data_ur, String adres, String telefon) {
            this.id_pracownika = id_pracownika;
            this.imie1 = imie1;
            this.imie2 = imie2;
            this.nazwisko = nazwisko;
            this.data_ur = data_ur;
            this.adres = adres;
            this.telefon = telefon;
        }

        public Employee(String imie1, String imie2, String nazwisko, String data_ur, String adres, String telefon) {
            this(-1, imie1, imie2, nazwisko, data_ur, adres, telefon);
        }
    }
    //wyswietlenie wszystkich pracownikow
    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String query = "SELECT * FROM pracownicy";

        try (Connection conn = Base.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                list.add(new Employee(
                        rs.getInt("id_pracownika"),
                        rs.getString("imie1"),
                        rs.getString("imie2"),
                        rs.getString("nazwisko"),
                        rs.getString("data_ur"),
                        rs.getString("adres"),
                        rs.getString("telefon")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Błąd pobierania pracowników: " + e.getMessage());
        }

        return list;
    }
    //dodanie nowego pracownika
    public boolean addEmployee(Employee emp) {
        String sql = "INSERT INTO pracownicy (imie1, imie2, nazwisko, data_ur, adres, telefon) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Base.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, emp.imie1);
            ps.setString(2, emp.imie2);
            ps.setString(3, emp.nazwisko);
            ps.setString(4, emp.data_ur);
            ps.setString(5, emp.adres);
            ps.setString(6, emp.telefon);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Błąd dodawania pracownika: " + e.getMessage());
            return false;
        }
    }
    //aktualizacja danych pracownika
    public boolean updateEmployee(Employee emp) {
        String sql = "UPDATE pracownicy SET imie1 = ?, imie2 = ?, nazwisko = ?, data_ur = ?, adres = ?, telefon = ? WHERE id_pracownika = ?";

        try (Connection conn = Base.connect();
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
    //usuwanie pracownika
    public boolean deleteEmployee(int id_pracownika) {
        String sql = "DELETE FROM pracownicy WHERE id_pracownika = ?";

        try (Connection conn = Base.connect();
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
