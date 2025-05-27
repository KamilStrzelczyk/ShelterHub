package org.emp.shelterhub.lib.db.entity;

public class EmployeeEntity {

  public int id_pracownika;
  public String imie1, imie2, nazwisko, data_ur, adres, telefon;

  public EmployeeEntity(
      int id_pracownika,
      String imie1,
      String imie2,
      String nazwisko,
      String data_ur,
      String adres,
      String telefon) {
    this.id_pracownika = id_pracownika;
    this.imie1 = imie1;
    this.imie2 = imie2;
    this.nazwisko = nazwisko;
    this.data_ur = data_ur;
    this.adres = adres;
    this.telefon = telefon;
  }

  public EmployeeEntity(
      String imie1, String imie2, String nazwisko, String data_ur, String adres, String telefon) {
    this(-1, imie1, imie2, nazwisko, data_ur, adres, telefon);
  }
}
