package org.emp.shelterhub.feature.room;

import java.util.ArrayList;
import java.util.List;
import org.emp.shelterhub.feature.room.data.Room;

public class RoomScreenState {
  private final List<Room> rooms;
  private final boolean loading;
  private final String errorMessage;

  private RoomScreenState(List<Room> rooms, boolean loading, String errorMessage) {
    this.rooms = rooms;
    this.loading = loading;
    this.errorMessage = errorMessage;
  }

  public static RoomScreenState initialState() {
    return new RoomScreenState(new ArrayList<>(), false, null);
  }

  public List<Room> getRooms() {
    return rooms;
  }

  public boolean isLoading() {
    return loading;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public RoomScreenState withRooms(List<Room> newRooms) {
    return new RoomScreenState(newRooms, this.loading, this.errorMessage);
  }

  public RoomScreenState withLoading(boolean loading) {
    return new RoomScreenState(this.rooms, loading, this.errorMessage);
  }

  public RoomScreenState withErrorMessage(String errorMessage) {
    return new RoomScreenState(this.rooms, this.loading, errorMessage);
  }
}
