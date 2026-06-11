package model.dao;

import model.entities.Seat;

import java.util.Optional;

public interface SeatDAO extends IDAO<Seat, Integer>{
    Optional<Seat> findSeat(Seat seat);
}
