package model.dao;

import model.config.HibernateConfig;
import model.entities.Room;
import model.entities.Seat;
import model.enums.SeatStatus;
import model.enums.SeatType;
import model.dao.impl.RoomImpl;
import model.dao.impl.SeatDAOImpl;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SeatDAOTest {

    private static SeatDAO seatDAO;
    private static RoomDAO roomDAO;
    private static Room testRoom;

    @BeforeAll
    static void setUp() {
        seatDAO = new SeatDAOImpl(Seat.class);
        roomDAO = new RoomImpl(Room.class);
        testRoom = new Room("SeatTest Room", 20);
        roomDAO.insert(testRoom);
    }

    @Test
    @Order(1)
    @DisplayName("Insert a new seat")
    void testInsert() {
        Seat seat = new Seat("A", 1, SeatStatus.AVAILABLE, SeatType.NORMAL, testRoom);
        assertDoesNotThrow(() -> seatDAO.insert(seat));
        assertTrue(seat.getId() > 0, "Seat ID should be generated after insert");
    }

    @Test
    @Order(2)
    @DisplayName("Find seat by ID")
    void testFindById() {
        Seat seat = new Seat("B", 2, SeatStatus.AVAILABLE, SeatType.VIP, testRoom);
        seatDAO.insert(seat);

        Optional<Seat> found = seatDAO.findById(seat.getId());
        assertTrue(found.isPresent(), "Seat should be found by ID");
        assertEquals("B", found.get().getSeatColumn());
        assertEquals(2, found.get().getSeatRow());
        assertEquals(SeatType.VIP, found.get().getSeatType());
    }

    @Test
    @Order(3)
    @DisplayName("Find seat by position (room + column + row)")
    void testFindSeat() {
        Seat seat = new Seat("C", 3, SeatStatus.BOOKED, SeatType.NORMAL, testRoom);
        seatDAO.insert(seat);

        Seat searchKey = new Seat("C", 3, null, null, testRoom);
        Optional<Seat> found = seatDAO.findSeat(searchKey);
        assertTrue(found.isPresent(), "Seat should be found by position");
        assertEquals(SeatStatus.BOOKED, found.get().getSeatStatus());
    }

    @Test
    @Order(4)
    @DisplayName("Find seat by position returns empty when not found")
    void testFindSeatNotFound() {
        Seat searchKey = new Seat("Z", 99, null, null, testRoom);
        Optional<Seat> found = seatDAO.findSeat(searchKey);
        assertFalse(found.isPresent(), "Should return empty for non-existent seat position");
    }

    @Test
    @Order(5)
    @DisplayName("Find all seats")
    void testFindAll() {
        List<Seat> seats = seatDAO.findAll();
        assertNotNull(seats, "Seat list should not be null");
        assertFalse(seats.isEmpty(), "Seat list should not be empty after inserts");
    }

    @Test
    @Order(6)
    @DisplayName("Update seat status")
    void testUpdate() {
        Seat seat = new Seat("D", 4, SeatStatus.AVAILABLE, SeatType.NORMAL, testRoom);
        seatDAO.insert(seat);

        seat.setSeatStatus(SeatStatus.BOOKED);
        seatDAO.update(seat);

        Optional<Seat> found = seatDAO.findById(seat.getId());
        assertTrue(found.isPresent());
        assertEquals(SeatStatus.BOOKED, found.get().getSeatStatus());
    }

    @Test
    @Order(7)
    @DisplayName("Delete seat")
    void testDelete() {
        Seat seat = new Seat("E", 5, SeatStatus.NOT_AVAILABLE, SeatType.NORMAL, testRoom);
        seatDAO.insert(seat);
        int id = seat.getId();

        seatDAO.delete(seat);

        Optional<Seat> found = seatDAO.findById(id);
        assertFalse(found.isPresent(), "Seat should be deleted");
    }

    @AfterAll
    static void tearDown() {
        List<Seat> seats = seatDAO.findAll();

        for (Seat seat : seats) {
            seatDAO.delete(seat);
        }

        roomDAO.delete(testRoom);

        HibernateConfig.getSessionFactory().close();
    }
}
