package fa.training.dao.impl;

import fa.training.dao.AbstractIDAOImpl;
import fa.training.dao.AirplaneDAO;
import fa.training.entities.Airplane;
import fa.training.entities.Seat;
import fa.training.enums.SeatStatus;
import fa.training.enums.SeatType;

import java.util.ArrayList;
import java.util.List;

public class AirplaneDAOImpl extends AbstractIDAOImpl<Airplane, Integer> implements AirplaneDAO {
    public AirplaneDAOImpl() {
        super(Airplane.class);
    }

    /*
        ID of seats: mode_airplane + _ + _ column + number
        HJB112_D1 , HJB112_B2
        total seat / 6 => rows
     */
    @Override
    public List<Seat> generateSeats(int airplaneId) {
        // Check existence
        Airplane airplane = findById(airplaneId).orElseThrow(
                () -> new IllegalArgumentException("Airplane with id " + airplaneId + " not found")
        );

        int totalSeats = airplane.getTotalSeats();
        int rows = totalSeats / 6;

        // Airplane usually has 6 columns (A,B,C,D,E,F)
        char []columns = {'A','B','C','D','E','F'};

        List<Seat> seats = new ArrayList<>();
        for(char c : columns){
            for(int row = 1; row <= rows; row++){
                Seat seat = new Seat();
                seat.setId(airplane.getModel() +"_" + c + row);
                seat.setAirplane(airplane);
                seat.setType(SeatType.ECONOMY); // default
                seat.setStatus(SeatStatus.AVAILABLE); // default
                seats.add(seat);
            }
        }

        executeInTransaction(session -> {
            Airplane managed = session.get(Airplane.class, airplaneId);
            if (managed != null) {
                for (Seat seat : seats) {
                    seat.setAirplane(managed);

                    // save each seat into seats table
                    session.persist(seat);
                }
            }
        });

        return seats;
    }

    /*
        DETAIL information: 7 columns
         model, length, width,
         total available economic seats: ECONOMIC + AVAILABLE
         total occupied economic seats: ECONOMIC + OCCUPIED
         total available business seats : BUSINESS + AVAILABLE
         and total occupied business seats: BUSINESS + OCCUPIED
     */
    @Override
    public List<Object[]> getAllAirplaneInDetail() {

        return execute(session -> {
           String hql =
                  """
                  SELECT a.model, a.length, a.width,
                  COALESCE ( SUM (CASE WHEN s.type = 'ECONOMY' AND s.status = 'AVAILABLE'THEN 1 ELSE 0 END), 0),
                  COALESCE ( SUM (CASE WHEN s.type = 'ECONOMY' AND s.status = 'OCCUPIED' THEN 1 ELSE 0 END), 0),
                  COALESCE ( SUM (CASE WHEN s.type = 'BUSINESS' AND s.status = 'AVAILABLE'THEN 1 ELSE 0 END), 0),
                  COALESCE ( SUM (CASE WHEN s.type = 'BUSINESS' AND s.status = 'OCCUPIED' THEN 1 ELSE 0 END), 0)
                  FROM Airplane a
                  LEFT JOIN a.seats s
                  GROUP BY a.model, a.length, a.width
                  """;
            return session.createQuery(hql,Object[].class).getResultList();
        });
    }

    /*
        all airplane width in range
     */
    @Override
    public List<Airplane> getAirplaneByWidth(int min, int max) {
        return execute(session -> {
            String hql = "FROM Airplane a WHERE a.width BETWEEN :min AND :max";
            return session.createQuery(hql, Airplane.class)
                    .setParameter("min", min)
                    .setParameter("max", max)
                    .getResultList();
        });
    }
}
