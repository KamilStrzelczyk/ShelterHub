package org.emp.shelterhub.feature.room.data;

public class Room {
  private final int roomNumber;
  private RoomType roomType;
  private RoomState roomState;

  public Room(int roomNumber, RoomType roomType, RoomState roomState) {
    this.roomNumber = roomNumber;
    this.roomType = roomType;
    this.roomState = roomState;
  }

  public int getRoomNumber() {
    return roomNumber;
  }

  public RoomType getRoomType() {
    return roomType;
  }

  public void setRoomType(RoomType roomType) {
    this.roomType = roomType;
  }

  public RoomState getRoomState() {
    return roomState;
  }

  public void setRoomState(RoomState roomState) {
    this.roomState = roomState;
  }

  public boolean isAvailable() {
    return roomState == RoomState.FREE;
  }

  public boolean isOccupied() {
    return roomState == RoomState.OCCUPIED;
  }

  public boolean isClean() {
    return roomState != RoomState.DIRTY;
  }

  public boolean hasMalfunction() {
    return roomState == RoomState.OUT_OF_ORDER;
  }
}
