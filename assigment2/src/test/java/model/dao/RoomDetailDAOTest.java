package model.dao;

import model.config.HibernateConfig;
import model.entities.Room;
import model.entities.RoomDetail;
import model.dao.impl.RoomImpl;
import model.dao.impl.RoomDetailDAOImpl;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoomDetailDAOTest {

    private static RoomDetailDAO roomDetailDAO;
    private static RoomDAO roomDAO;
    private static Room testRoom;
    private static int insertedDetailId;

    @BeforeAll
    static void setUp() {
        roomDetailDAO = new RoomDetailDAOImpl(RoomDetail.class);
        roomDAO = new RoomImpl(Room.class);
        testRoom = new Room("DetailTest Room", 30);
        roomDAO.insert(testRoom);
    }

    @AfterAll
    static void tearDown() {
        if (testRoom != null) {
            roomDAO.delete(testRoom);
        }
        if (HibernateConfig.getSessionFactory() != null) {
            HibernateConfig.getSessionFactory().close();
        }
    }

    @Test
    @Order(1)
    @DisplayName("Insert a new room detail")
    void testInsert() {
        RoomDetail detail = new RoomDetail(50000, LocalDate.of(2025, 6, 1), "Standard room with Dolby sound", testRoom);
        assertDoesNotThrow(() -> roomDetailDAO.insert(detail));
        assertTrue(detail.getId() > 0, "RoomDetail ID should be generated after insert");
        insertedDetailId = detail.getId();
    }

    @Test
    @Order(2)
    @DisplayName("Find room detail by ID")
    void testFindById() {
        Optional<RoomDetail> found = roomDetailDAO.findById(insertedDetailId);
        assertTrue(found.isPresent(), "RoomDetail should be found by ID");
        assertEquals(50000, found.get().getRate());
        assertEquals("Standard room with Dolby sound", found.get().getDescription());
        assertEquals(LocalDate.of(2025, 6, 1), found.get().getActiveDate());
    }

    @Test
    @Order(3)
    @DisplayName("Find all room details")
    void testFindAll() {
        List<RoomDetail> details = roomDetailDAO.findAll();
        assertNotNull(details, "RoomDetail list should not be null");
        assertFalse(details.isEmpty(), "RoomDetail list should not be empty after inserts");
    }

    @Test
    @Order(4)
    @DisplayName("Update room detail")
    void testUpdate() {
        // Retrieve the existing detail from Order(1) and update it
        List<RoomDetail> allDetails = roomDetailDAO.findAll();
        assertFalse(allDetails.isEmpty(), "Should have at least one detail to update");
        RoomDetail detail = allDetails.get(0);

        detail.setRate(65000);
        detail.setDescription("Updated pricing");
        roomDetailDAO.update(detail);

        Optional<RoomDetail> found = roomDetailDAO.findById(detail.getId());
        assertTrue(found.isPresent());
        assertEquals(65000, found.get().getRate());
        assertEquals("Updated pricing", found.get().getDescription());
    }
}
