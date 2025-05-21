package org.emp.shelterhub.feature.room;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.emp.shelterhub.feature.room.data.Room;
import org.emp.shelterhub.feature.room.data.RoomType;

public class RoomScreenViewModel {
  private List<Room> rooms;

  public RoomScreenViewModel() {
    this.rooms =
        IntStream.rangeClosed(101, 200)
            .mapToObj(i -> new Room(i, RoomType.SINGLE))
            .collect(Collectors.toList());
  }

  public List<Room> getRooms() {
    return rooms;
  }

  public void handleRoomClick(Room room) {
    System.out.println("Room clicked: " + room.getRoomNumber());
  }

  public void handleRoomUpdate(Room room) {
    System.out.println("Room clicked: " + room.getRoomNumber());
  }
}
