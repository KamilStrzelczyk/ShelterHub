package org.emp.shelterhub.lib.db.entity;

public class RoomEntity {
  public int kod_pokoj;
  public String stan;
  public String typ;
  public double cena;

  public RoomEntity(int kod_pokoj, String stan, String typ, double cena) {
    this.kod_pokoj = kod_pokoj;
    this.stan = stan;
    this.typ = typ;
    this.cena = cena;
  }
}
