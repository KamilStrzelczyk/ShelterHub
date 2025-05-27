package org.emp.shelterhub.lib.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.emp.shelterhub.feature.room.data.Room;
import org.emp.shelterhub.lib.db.dao.RoomsDAO;
import org.emp.shelterhub.lib.db.entity.RoomEntity;
import org.emp.shelterhub.lib.infrastructure.mapper.RoomMapper;

public class RoomRepository {

  private static final RoomsDAO roomsDAO = new RoomsDAO();

  public List<Room> getAllRooms() {
    return roomsDAO.getRooms().stream().map(RoomMapper::toDomain).collect(Collectors.toList());
  }

  public List<Room> getAvailableRooms() {
    return roomsDAO.getAvailableRooms().stream()
        .map(RoomMapper::toDomain)
        .collect(Collectors.toList());
  }

  public boolean addRoom(Room room) {
    RoomEntity entity = RoomMapper.toEntity(room);
    return roomsDAO.addRoom(entity);
  }

  public boolean updateRoom(Room room) {
    RoomEntity entity = RoomMapper.toEntity(room);
    return roomsDAO.updateRoom(entity);
  }

  public boolean deleteRoom(int roomNumber) {
    return roomsDAO.deleteRoom(roomNumber);
  }

  public Optional<Room> getRoomById(int roomNumber) {
    return roomsDAO.getRooms().stream()
        .filter(entity -> entity.kod_pokoj == roomNumber)
        .findFirst()
        .map(RoomMapper::toDomain);
  }
}
