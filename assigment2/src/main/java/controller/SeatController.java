package controller;

import model.dao.impl.SeatDAOImpl;
import model.entities.Seat;
import model.services.SeatService;

import java.util.List;

public class SeatController {
    private final SeatService seatService = new SeatService(new SeatDAOImpl(Seat.class));

    public SeatController() {

    }

    public void addSeat(Seat seat) {
        seatService.addSeat(seat);
    }

    public void updateSeat(Seat seat) {
        seatService.updateSeat(seat);
    }

    public void deleteSeat(Seat seat) {
        seatService.deleteSeat(seat);
    }

    public List<Seat> findAllSeats() {
        return seatService.findAllSeats();
    }

    public Seat findSeat(Seat seat) {
        return seatService.findSeatByPosition(seat);
    }
}
