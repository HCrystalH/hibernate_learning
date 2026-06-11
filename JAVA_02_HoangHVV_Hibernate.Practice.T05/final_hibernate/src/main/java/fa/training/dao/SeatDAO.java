package fa.training.dao;

import fa.training.entities.Seat;

import java.util.List;

public interface SeatDAO extends IDAO<Seat, String> {
    List<Seat> findSeatByAirplaneId(int airplaneId);
}
