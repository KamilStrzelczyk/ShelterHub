package org.emp.shelterhub.lib.db;

import java.sql.*;

public class Base {
  public static void main(String[] args) {
    String url = "jdbc:sqlite:shelterhub.db";

    try (Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement()) {

      if (conn != null) {
        // Włącz obsługę kluczy obcych (tylko w SQLite trzeba to robić ręcznie)
        stmt.execute("PRAGMA foreign_keys = ON");

        // Tabele
        stmt.execute(
            """
                    CREATE TABLE IF NOT EXISTS stanowisko (
                        id_stanowiska INTEGER PRIMARY KEY AUTOINCREMENT,
                        nazwa TEXT CHECK (nazwa IN ('kierownik', 'pracownik obslugi'))
                    );
                """);

        stmt.execute(
            """
                    CREATE TABLE IF NOT EXISTS pracownicy (
                        id_pracownika INTEGER PRIMARY KEY AUTOINCREMENT,
                        imie1 TEXT NOT NULL,
                        imie2 TEXT,
                        nazwisko TEXT NOT NULL,
                        data_ur TEXT,
                        adres TEXT,
                        telefon TEXT UNIQUE
                    );
                """);

        stmt.execute(
            """
                    CREATE TABLE IF NOT EXISTS zatrudnienie (
                        id_zatrudnienia INTEGER PRIMARY KEY AUTOINCREMENT,
                        id_pracownika INTEGER NOT NULL,
                        id_stanowiska INTEGER NOT NULL,
                        od TEXT,
                        do TEXT,
                        FOREIGN KEY (id_pracownika) REFERENCES pracownicy(id_pracownika),
                        FOREIGN KEY (id_stanowiska) REFERENCES stanowisko(id_stanowiska),
                        CHECK (do IS NULL OR do > od)
                    );
                """);

        stmt.execute(
            """
                    CREATE TABLE IF NOT EXISTS pensja (
                        id_pensja INTEGER PRIMARY KEY AUTOINCREMENT,
                        id_pracownika INTEGER NOT NULL,
                        pensja_brutto REAL NOT NULL CHECK (pensja_brutto >= 0),
                        data_wyplaty TEXT,
                        premia REAL DEFAULT 0 CHECK (premia >= 0),
                        FOREIGN KEY (id_pracownika) REFERENCES pracownicy(id_pracownika)
                    );
                """);

        stmt.execute(
            """
                    CREATE TABLE IF NOT EXISTS goscie (
                        id_gosc INTEGER PRIMARY KEY AUTOINCREMENT,
                        imie TEXT NOT NULL,
                        nazwisko TEXT NOT NULL,
                        dokument_tozsamosc TEXT UNIQUE,
                        nr_tel TEXT
                    );
                """);

        stmt.execute(
            """
                    CREATE TABLE IF NOT EXISTS pokoje (
                        kod_pokoj INTEGER PRIMARY KEY AUTOINCREMENT,
                        dostepnosc TEXT CHECK (dostepnosc IN ('dostepny', 'zajety')),
                        stan TEXT CHECK (stan IN ('brudny', 'czysty')),
                        typ TEXT CHECK (typ IN ('1-osobowy', '2-osobowy', '3-osobowy')),
                        cena_za_noc REAL
                    );
                """);

        stmt.execute(
            """
                    CREATE TABLE IF NOT EXISTS rezerwacja (
                        id_rezerwacji INTEGER PRIMARY KEY AUTOINCREMENT,
                        id_gosc INTEGER NOT NULL,
                        kod_pokoj INTEGER NOT NULL,
                        data_przyjazdu TEXT,
                        data_wyjazdu TEXT,
                        oplata TEXT,
                        potwierdzenie TEXT,
                        FOREIGN KEY (id_gosc) REFERENCES goscie(id_gosc),
                        FOREIGN KEY (kod_pokoj) REFERENCES pokoje(kod_pokoj),
                        CHECK (data_wyjazdu > data_przyjazdu)
                    );
                """);

        System.out.println("Baza danych została utworzona pomyślnie.");
      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
