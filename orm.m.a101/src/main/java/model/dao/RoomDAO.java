package model.dao;

import model.entities.Room;

import java.util.Optional;

public interface RoomDAO extends IDAO<Room, Integer> {
    Optional<Room> findByName(String name);
}
