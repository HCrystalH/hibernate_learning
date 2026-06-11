package model.services;

import model.dao.RoomDAO;
import model.entities.Room;

import java.util.List;
import java.util.Optional;

/**
    similar logic to seat service
    name must be unique
 */
public class RoomService {
    private final RoomDAO roomDAO;

    public RoomService(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

    public Room findById(int id) {
        return roomDAO.findById(id).orElse(null);
    }

    public List<Room> findAll() {
        return roomDAO.findAll();
    }

    public Optional<Room> findRoomByName(String name) {
        return roomDAO.findByName(name);
    }

    public void addRoom(Room room) {
        if(findRoomByName(room.getName()).isPresent()) {
            throw new IllegalArgumentException("Room with name " + room.getName() + " already exists.");
        }
        roomDAO.insert(room);
    }

    public void removeRoom(int id) {
        Room room = findById(id);
        if(room == null) {
            throw new IllegalArgumentException("Room with id " + id + " does not exist.");
        }
        roomDAO.delete(room);
    }

    /*
        Check existence
        Check name ? (same ID) -> update : diff -> non-update
        Ex: rename "room A" -> "room B" but "room B" existed
     */
    public void updateRoom(Room room) {
        Room exist =  findById(room.getId());
        if(exist == null) {
            throw new IllegalArgumentException("Room with id " + room.getId() + " does not exist.");
        }
        // Find room with same name
        Optional<Room> roomWithSameName = roomDAO.findByName(room.getName());
        if(roomWithSameName.isPresent() && (roomWithSameName.get().getId() != exist.getId())) {
            throw new IllegalArgumentException("Room with name " + room.getName() + " already exists.");
        }

        roomDAO.update(room);
    }
}
