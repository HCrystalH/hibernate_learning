import controller.RoomController;
import controller.SeatController;
import model.config.HibernateConfig;
import model.dao.RoomDetailDAO;
import model.dao.impl.RoomDetailDAOImpl;
import model.entities.Room;
import model.entities.RoomDetail;
import model.entities.Seat;
import model.enums.SeatStatus;
import model.enums.SeatType;

import java.time.LocalDate;
import java.util.List;

/**
 * Movie Theater Simulation
 * Demonstrates CRUD operations for Room, Seat, and RoomDetail entities.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  Movie Theater Management Simulation  ");
        System.out.println("========================================\n");

        RoomController roomController = new RoomController();
        SeatController seatController = new SeatController();
        RoomDetailDAO roomDetailDAO = new RoomDetailDAOImpl(RoomDetail.class);

        try {
            // ========== ROOM OPERATIONS ==========
            System.out.println("--- ROOM OPERATIONS ---\n");

            // CREATE rooms
            System.out.println(">> Creating rooms...");
            Room room1 = new Room("Hall A - Standard", 50);
            Room room2 = new Room("Hall B - VIP", 80);
            roomController.addRoom(room1);
            roomController.addRoom(room2);
            System.out.println("   Created: " + room1.getName() + " (ID: " + room1.getId() + ")");
            System.out.println("   Created: " + room2.getName() + " (ID: " + room2.getId() + ")");

            // READ all rooms
            System.out.println("\n>> Listing all rooms...");
            List<Room> rooms = roomController.findAll();
            rooms.forEach(r -> System.out.println("   Room: " + r.getName()
                    + " | Seats: " + r.getSeatQuantity()
                    + " | ID: " + r.getId()));

            // UPDATE a room
            System.out.println("\n>> Updating room name...");
            room1.setName("Hall A - Premium Standard");
            roomController.updateRoom(room1);
            System.out.println("   Updated to: " + room1.getName());

            // FIND room by name
            System.out.println("\n>> Finding room by name 'Hall B - VIP'...");
            roomController.findRoom("Hall B - VIP").ifPresentOrElse(
                    r -> System.out.println("   Found: " + r.getName()),
                    () -> System.out.println("   NOT FOUND")
            );

            // ========== SEAT OPERATIONS ==========
            System.out.println("\n--- SEAT OPERATIONS ---\n");

            // CREATE seats
            System.out.println(">> Adding seats to " + room1.getName() + "...");
            Seat seat1 = new Seat("A", 1, SeatStatus.AVAILABLE, SeatType.NORMAL, room1);
            Seat seat2 = new Seat("A", 2, SeatStatus.AVAILABLE, SeatType.NORMAL, room1);
            Seat seat3 = new Seat("B", 1, SeatStatus.AVAILABLE, SeatType.VIP, room1);
            Seat seat4 = new Seat("B", 2, SeatStatus.BOOKED, SeatType.VIP, room1);
            seatController.addSeat(seat1);
            seatController.addSeat(seat2);
            seatController.addSeat(seat3);
            seatController.addSeat(seat4);
            System.out.println("   Added seat: " + seat1.getSeatColumn() + seat1.getSeatRow()
                    + " [" + seat1.getSeatType() + "] Status: " + seat1.getSeatStatus());
            System.out.println("   Added seat: " + seat2.getSeatColumn() + seat2.getSeatRow()
                    + " [" + seat2.getSeatType() + "] Status: " + seat2.getSeatStatus());
            System.out.println("   Added seat: " + seat3.getSeatColumn() + seat3.getSeatRow()
                    + " [" + seat3.getSeatType() + "] Status: " + seat3.getSeatStatus());
            System.out.println("   Added seat: " + seat4.getSeatColumn() + seat4.getSeatRow()
                    + " [" + seat4.getSeatType() + "] Status: " + seat4.getSeatStatus());

            // READ all seats
            System.out.println("\n>> Listing all seats...");
            List<Seat> seats = seatController.findAllSeats();
            seats.forEach(s -> System.out.println("   Seat: " + s.getSeatColumn() + s.getSeatRow()
                    + " | Type: " + s.getSeatType()
                    + " | Status: " + s.getSeatStatus()
                    + " | Room ID: " + s.getRoom().getId()));

            // UPDATE seat status (book a seat)
            System.out.println("\n>> Booking seat A1...");
            seat1.setSeatStatus(SeatStatus.BOOKED);
            seatController.updateSeat(seat1);
            System.out.println("   Seat A1 status updated to: " + seat1.getSeatStatus());

            // FIND seat by position
            System.out.println("\n>> Finding seat B1 by position...");
            Seat searchKey = new Seat("B", 1, null, null, room1);
            Seat found = seatController.findSeat(searchKey);
            System.out.println("   Found: " + found.getSeatColumn() + found.getSeatRow()
                    + " | Type: " + found.getSeatType()
                    + " | Status: " + found.getSeatStatus());

            // ========== ROOM DETAIL OPERATIONS ==========
            System.out.println("\n--- ROOM DETAIL OPERATIONS ---\n");

            // CREATE room details
            System.out.println(">> Adding room details...");
            RoomDetail detail1 = new RoomDetail(50000, LocalDate.of(2025, 6, 1),
                    "Standard hall with Dolby Atmos sound system", room1);
            RoomDetail detail2 = new RoomDetail(80000, LocalDate.of(2025, 7, 1),
                    "VIP hall with IMAX and recliner seats", room2);
            roomDetailDAO.insert(detail1);
            roomDetailDAO.insert(detail2);
            System.out.println("   Detail for " + room1.getName()
                    + ": rate=" + detail1.getRate()
                    + ", active=" + detail1.getActiveDate()
                    + ", desc=" + detail1.getDescription());
            System.out.println("   Detail for " + room2.getName()
                    + ": rate=" + detail2.getRate()
                    + ", active=" + detail2.getActiveDate()
                    + ", desc=" + detail2.getDescription());

            // READ all room details
            System.out.println("\n>> Listing all room details...");
            List<RoomDetail> details = roomDetailDAO.findAll();
            details.forEach(d -> System.out.println("   Detail ID: " + d.getId()
                    + " | Rate: " + d.getRate()
                    + " | Active: " + d.getActiveDate()
                    + " | Desc: " + d.getDescription()));

            // UPDATE a room detail
            System.out.println("\n>> Updating room detail rate...");
            detail1.setRate(55000);
            detail1.setDescription("Standard hall with Dolby Atmos - updated pricing");
            roomDetailDAO.update(detail1);
            RoomDetail updated = roomDetailDAO.findById(detail1.getId()).orElseThrow();
            System.out.println("   Updated: rate=" + updated.getRate()
                    + ", desc=" + updated.getDescription());

            // ========== DELETE OPERATIONS ==========
            System.out.println("\n--- DELETE OPERATIONS ---\n");

            // Delete a seat
            System.out.println(">> Deleting seat " + seat2.getSeatColumn() + seat2.getSeatRow() + "...");
            seatController.deleteSeat(seat2);
            System.out.println("   Deleted successfully.");

            // Verify deletion - list remaining seats
            System.out.println(">> Listing all seats after deletion...");
            seatController.findAllSeats().forEach(s ->
                    System.out.println("   Seat: " + s.getSeatColumn() + s.getSeatRow()
                            + " | Status: " + s.getSeatStatus()));

            System.out.println("\n========================================");
            System.out.println("  Simulation completed successfully!  ");
            System.out.println("========================================");

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        } finally {
            HibernateConfig.getSessionFactory().close();
        }
    }
}
