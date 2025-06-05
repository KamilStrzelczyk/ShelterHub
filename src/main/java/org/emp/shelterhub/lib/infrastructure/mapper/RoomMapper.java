package org.emp.shelterhub.lib.infrastructure.mapper;

import org.emp.shelterhub.feature.room.data.Room;
import org.emp.shelterhub.feature.room.data.RoomState;
import org.emp.shelterhub.feature.room.data.RoomType;
import org.emp.shelterhub.lib.db.entity.RoomEntity;

public class RoomMapper {

  public static Room toDomain(RoomEntity entity) {
    Room room =
        new Room(
            entity.kod_pokoj, RoomType.fromDbValue(entity.typ), RoomState.fromDbValue(entity.stan));
    return room;
  }

  public static RoomEntity toEntity(Room room) {
    return new RoomEntity(
        room.getRoomNumber(),
        room.getRoomState().getDbValue(),
        room.getRoomType().getDbValue(),
        0.0);
  }
}
