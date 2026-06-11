package controller;

import model.dao.impl.RoomImpl;
import model.entities.Room;
import model.services.RoomService;

import java.util.List;
import java.util.Optional;

public class RoomController {
    private final RoomService roomService = new RoomService(new RoomImpl(Room.class));

    public List<Room> findAll() {
        return roomService.findAll();
    }

    public Optional<Room> findRoom(String name) {
        return roomService.findRoomByName(name);
    }

    public void removeRoom(int id) {
        roomService.removeRoom(id);
    }

    public void updateRoom(Room room) {
        roomService.updateRoom(room);
    }

    public void addRoom(Room room) {
        roomService.addRoom(room);
    }
}
