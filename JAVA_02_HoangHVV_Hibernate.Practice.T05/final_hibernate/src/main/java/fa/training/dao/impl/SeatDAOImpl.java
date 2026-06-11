package fa.training.dao.impl;

import fa.training.dao.AbstractIDAOImpl;
import fa.training.dao.SeatDAO;
import fa.training.entities.Seat;

import java.util.List;

public class SeatDAOImpl extends AbstractIDAOImpl<Seat, String> implements SeatDAO {

    public SeatDAOImpl() {
        super(Seat.class);
    }

    @Override
    public List<Seat> findSeatByAirplaneId(int airplaneId) {
        return execute(session -> {
            String hql = "FROM Seat s WHERE s.airplane.id = :airplaneId";
            return session.createQuery(hql, Seat.class)
                    .setParameter("airplaneId", airplaneId)
                    .getResultList();
        });
    }
}
