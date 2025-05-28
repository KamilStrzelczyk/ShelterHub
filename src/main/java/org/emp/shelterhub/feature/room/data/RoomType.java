package org.emp.shelterhub.feature.room.data;

public enum RoomType {
  SINGLE("1-osobowy"),
  DOUBLE("2-osobowy"),
  FAMILY("3-osobowy");

  private final String dbValue;

  RoomType(String dbValue) {
    this.dbValue = dbValue;
  }

  public String getDbValue() {
    return dbValue;
  }

  public static RoomType fromDbValue(String value) {
    for (RoomType type : values()) {
      if (type.dbValue.equalsIgnoreCase(value)) {
        return type;
      }
    }
    return SINGLE; // domyślnie
  }
}
