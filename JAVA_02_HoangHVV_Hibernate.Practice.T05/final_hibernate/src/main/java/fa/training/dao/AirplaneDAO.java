package fa.training.dao;

import fa.training.entities.Airplane;
import fa.training.entities.Seat;

import java.util.List;

public interface AirplaneDAO extends IDAO<Airplane, Integer> {
    List<Seat> generateSeats(int airplaneId);
    List<Object[]> getAllAirplaneInDetail();
    List<Airplane> getAirplaneByWidth(int min, int max);
}
