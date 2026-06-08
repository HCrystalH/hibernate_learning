package model.dao;

import model.config.HibernateConfig;
import model.entities.Room;
import model.dao.impl.RoomImpl;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoomDAOTest {

    private static RoomDAO roomDAO;

    @BeforeAll
    static void setUp() {
        roomDAO = new RoomImpl(Room.class);
    }

    @AfterAll
    static void tearDown() {
        if (HibernateConfig.getSessionFactory() != null) {
            HibernateConfig.getSessionFactory().close();
        }
    }

    @Test
    @Order(1)
    @DisplayName("Insert a new room")
    void testInsert() {
        Room room = new Room("Room A", 50);
        assertDoesNotThrow(() -> roomDAO.insert(room));
        assertNotNull(room.getId() > 0 ? room : null, "Room ID should be generated after insert");
    }

    @Test
    @Order(2)
    @DisplayName("Find room by ID")
    void testFindById() {
        // Insert first
        Room room = new Room("Room B", 80);
        roomDAO.insert(room);

        Optional<Room> found = roomDAO.findById(room.getId());
        assertTrue(found.isPresent(), "Room should be found by ID");
        assertEquals("Room B", found.get().getName());
        assertEquals(80, found.get().getSeatQuantity());
    }

    @Test
    @Order(3)
    @DisplayName("Find room by name")
    void testFindByName() {
        Room room = new Room("Room C", 100);
        roomDAO.insert(room);

        Optional<Room> found = roomDAO.findByName("Room C");
        assertTrue(found.isPresent(), "Room should be found by name");
        assertEquals("Room C", found.get().getName());
    }

    @Test
    @Order(4)
    @DisplayName("Find by name returns empty when not found")
    void testFindByNameNotFound() {
        Optional<Room> found = roomDAO.findByName("NonExistentRoom_XYZ");
        assertFalse(found.isPresent(), "Should return empty for non-existent room name");
    }

    @Test
    @Order(5)
    @DisplayName("Find all rooms")
    void testFindAll() {
        List<Room> rooms = roomDAO.findAll();
        assertNotNull(rooms, "Room list should not be null");
        assertFalse(rooms.isEmpty(), "Room list should not be empty after inserts");
    }

    @Test
    @Order(6)
    @DisplayName("Update room by ID")
    void testUpdate() {
        Room room = new Room("Room D", 60);
        roomDAO.insert(room);

        room.setName("Room D Updated");
        room.setSeatQuantity(70);
        roomDAO.update(room);

        Optional<Room> found = roomDAO.findById(room.getId());
        assertTrue(found.isPresent());
        assertEquals("Room D Updated", found.get().getName());
        assertEquals(70, found.get().getSeatQuantity());
    }

    @Test
    @Order(7)
    @DisplayName("Delete room by entity")
    void testDelete() {
        Room room = new Room("Room E", 40);
        roomDAO.insert(room);
        int id = room.getId();

        roomDAO.delete(room);

        Optional<Room> found = roomDAO.findById(id);
        assertFalse(found.isPresent(), "Room should be deleted");
    }
}
