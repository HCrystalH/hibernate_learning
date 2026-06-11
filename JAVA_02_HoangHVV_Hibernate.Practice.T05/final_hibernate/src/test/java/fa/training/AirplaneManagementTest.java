package fa.training;

import fa.training.dao.AirplaneDAO;
import fa.training.dao.SeatDAO;
import fa.training.dao.impl.AirplaneDAOImpl;
import fa.training.dao.impl.SeatDAOImpl;
import fa.training.entities.*;
import fa.training.enums.*;
import jakarta.validation.*;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AirplaneManagementTest {
    private static AirplaneDAO airplaneDAO;
    private static SeatDAO seatDAO;

    private static Validator validator;
    private static Airplane testAirplane;
    private static int airplaneId;

    // Use H2 to test
    @BeforeAll
    static void setUp() {
        airplaneDAO = new AirplaneDAOImpl();
        seatDAO = new SeatDAOImpl();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /*
        Happy case: create airplane -> create seats
        save -> retrieve
            + getAirplaneByWidth
            + getAllAirplaneInDetail
            + findSeatByAirplaneId
     */
    @Test
    @Order(1)
    void testSaveAirplane() {
        Airplane airplane = new Airplane();
        airplane.setModel("HJB112");
        airplane.setLength(50);
        airplane.setWidth(10);
        airplane.setTotalSeats(12);

//        System.out.println(airplane);

        Set<ConstraintViolation<Airplane>> violations = validator.validate(airplane);
        assertTrue(violations.isEmpty(), "Airplane should be valid");

        airplaneDAO.save(airplane);
        assertNotNull(airplane.getId(), "Airplane ID should be generated after save");

        Airplane afterSaved = airplaneDAO.findById(airplane.getId()).orElse(null);

        assertNotNull(afterSaved);

        // After saved -> every information must have
        assertEquals("HJB112", afterSaved.getModel());
        assertEquals(50, afterSaved.getLength());
        assertEquals(10, afterSaved.getWidth());
        assertEquals(12, afterSaved.getTotalSeats());
        assertNotNull(afterSaved.getCreatedDate());

        testAirplane = afterSaved;
        airplaneId = afterSaved.getId();

//        System.out.println(airplane);
    }

    @Test
    @Order(2)
    void testGenerateSeats() {
        assertNotNull(testAirplane, "testAirplane should not be null");

        List<Seat> seats = airplaneDAO.generateSeats(airplaneId);
        assertNotNull(seats);

        // Must equal total seat
        assertEquals(12, seats.size());

        for (Seat seat : seats) {
            assertTrue(seat.getId().startsWith("HJB112_"));
            assertEquals(SeatType.ECONOMY, seat.getType());
            assertEquals(SeatStatus.AVAILABLE, seat.getStatus());
            assertNotNull(seat.getCreatedDate());
        }
    }

    // test case to test save an Airplane with a list Seat
    @Test
    @Order(3)
    void testSaveAirplaneWithSeats() {
        Airplane airplane = new Airplane();
        airplane.setModel("ABC123");
        airplane.setLength(60);
        airplane.setWidth(15);
        airplane.setTotalSeats(6);

        airplaneDAO.save(airplane);

        List<Seat> seats = airplaneDAO.generateSeats(airplane.getId());
        assertEquals(6, seats.size());

//        Airplane found = airplaneDAO.findById(airplane.getId()).orElse(null);
        Airplane found = airplaneDAO.findByIdWithFetch(
                airplane.getId(), "seats")
                .orElse(null);

        assertNotNull(found);
        assertNotNull(found.getSeats());
        assertEquals(6, found.getSeats().size());
    }

    @Test
    @Order(4)
    void testGetAirplaneByWidth() {
        List<Airplane> result = airplaneDAO.getAirplaneByWidth(5, 12);

        // empty -> false
        assertFalse(result.isEmpty());

        for (Airplane a : result) {
            //expected true
            assertTrue(a.getWidth() >= 5 && a.getWidth() <= 12);
        }
    }

    @Test
    @Order(5)
    void testGetAllAirplaneInDetail() {
        List<Object[]> details = airplaneDAO.getAllAirplaneInDetail();
        assertFalse(details.isEmpty());

        for (Object[] col : details) {
            // DETAIL information: 7 columns
            assertEquals(7, col.length);

            assertTrue(col[0] instanceof String);   // model
            assertTrue(col[1] instanceof Number);   // length
            assertTrue(col[2] instanceof Number);   // width
        }
    }

    @Test
    @Order(6)
    void testFindSeatsByAirplaneId() {
        List<Seat> seats = seatDAO.findSeatByAirplaneId(airplaneId);
        assertNotNull(seats);
        assertEquals(12, seats.size());
    }

    // delete airplane
    @Test
    @Order(7)
    void testDeleteAirplane() {
        int tempId = testAirplane.getId();
        assertEquals(false, seatDAO.findSeatByAirplaneId(tempId).isEmpty(), "Seat should exist");

        airplaneDAO.delete(testAirplane);

        assertEquals(true, airplaneDAO.findById(tempId).isEmpty(), "Airplane should be deleted");
        assertEquals(true, seatDAO.findSeatByAirplaneId(tempId).isEmpty(), "Seat should be deleted following airplane");
    }

    /*
        Abnormal cases:
            + model : contains special character
            + model : length > 6
            + total seats not divisible by 6
     */
    // special characters
    @Test
    @Order(8)
    void testAbnormalCase1() {
        Airplane airplane = new Airplane();

        // contains special characters
        airplane.setModel("HJ*112");
        airplane.setLength(50);
        airplane.setWidth(10);
        airplane.setTotalSeats(12);

        Set<ConstraintViolation<Airplane>> violations = validator.validate(airplane);
        assertFalse(violations.isEmpty(), "Model with special chars should be invalid");

        boolean hasModelViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("model"));
        assertTrue(hasModelViolation);
    }

    // length > 6
    @Test
    @Order(9)
    void testAbnormalCase2() {
        Airplane airplane = new Airplane();
        airplane.setModel("HJB12345633");
        airplane.setLength(50);
        airplane.setWidth(10);
        airplane.setTotalSeats(12);

        Set<ConstraintViolation<Airplane>> violations = validator.validate(airplane);
        assertFalse(violations.isEmpty(), "Model with wrong length should be invalid");
    }

    // not divisible by 6
    @Test
    @Order(10)
    void testAbnormalCase3() {
        Airplane airplane = new Airplane();
        airplane.setModel("TEST12");
        airplane.setLength(50);
        airplane.setWidth(10);
        airplane.setTotalSeats(10);

        Set<ConstraintViolation<Airplane>> violations = validator.validate(airplane);
        assertFalse(violations.isEmpty(), "Total seats not divisible by 6 should be invalid");

        boolean hasDivisibleViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("totalSeats"));
        assertTrue(hasDivisibleViolation);
    }
}
