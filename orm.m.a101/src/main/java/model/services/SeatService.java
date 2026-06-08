package model.services;

import model.dao.SeatDAO;
import model.entities.Seat;
import model.exceptions.SeatException;

import java.util.List;

public class SeatService {
    private final SeatDAO seatDAO;

    public SeatService(SeatDAO seatDAO) {
        this.seatDAO = seatDAO;
    }

    public void addSeat(Seat seat) {
        // Check existence by Room ID + Row + Column
        if(seatDAO.findSeat(seat).isPresent()){
            throw new SeatException("Seat is already exist");
        }

        seatDAO.insert(seat);
    }

    public void updateSeat(Seat seat) {
        findById(seat.getId());

        seatDAO.update(seat);
    }

    public List<Seat> findAllSeats() {
        return seatDAO.findAll();
    }

    public void deleteSeat(Seat seat) {
        Seat exist = findById(seat.getId());
        seatDAO.delete(exist);
    }

    public Seat findSeatByPosition(Seat seat) {
        return seatDAO.findSeat(seat).orElseThrow(
                () -> new SeatException(
                        String.format("Seat %s %d is NOT FOUND in room %d",
                            seat.getSeatColumn(),
                            seat.getSeatRow(),
                            seat.getRoom().getId()
                        )
                )
        );
    }

    private Seat findById(int id) {
        return seatDAO.findById(id).orElseThrow(()-> new  SeatException("Seat with ID " + id + " does not exist."));
    }
}
