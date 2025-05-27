package org.emp.shelterhub.feature.room.data;

public enum RoomState {
  FREE("czysty"),
  OCCUPIED("zajety"),
  DIRTY("brudny"),
  OUT_OF_ORDER("awaria");

  private final String dbValue;

  RoomState(String dbValue) {
    this.dbValue = dbValue;
  }

  public String getDbValue() {
    return dbValue;
  }

  public static RoomState fromDbValue(String value) {
    for (RoomState state : values()) {
      if (state.dbValue.equalsIgnoreCase(value)) {
        return state;
      }
    }
    return FREE; // domyślnie
  }
}
