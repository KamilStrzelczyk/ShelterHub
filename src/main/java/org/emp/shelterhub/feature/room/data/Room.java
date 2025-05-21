package org.emp.shelterhub.feature.room.data;

public class Room {
  private int roomNumber;
  private boolean isOccupied;
  private boolean isClean;
  private boolean isAvailable;
  private boolean hasMalfunction;
  private RoomType roomType;

  public Room(int roomNumber, RoomType roomType) {
    this.roomNumber = roomNumber;
    this.roomType = roomType;
    this.isOccupied = false;
    this.isClean = true;
    this.isAvailable = true;
    this.hasMalfunction = false;
  }

  public int getRoomNumber() {
    return roomNumber;
  }

  public boolean isOccupied() {
    return isOccupied;
  }

  public boolean isClean() {
    return isClean;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public boolean hasMalfunction() {
    return hasMalfunction;
  }

  public RoomType getRoomType() {
    return roomType;
  }

  public void setOccupied(boolean occupied) {
    this.isOccupied = occupied;
  }

  public void setClean(boolean clean) {
    this.isClean = clean;
  }

  public void setAvailable(boolean available) {
    this.isAvailable = available;
  }

  public void setMalfunction(boolean malfunction) {
    this.hasMalfunction = malfunction;
  }

  public void setRoomType(RoomType roomType) {
    this.roomType = roomType;
  }
}
