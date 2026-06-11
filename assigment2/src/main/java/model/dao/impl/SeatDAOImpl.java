package model.dao.impl;

import model.config.HibernateConfig;
import model.entities.Seat;
import model.dao.SeatDAO;
import model.exceptions.DataAccessException;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.Optional;

public class SeatDAOImpl extends AbstractIDAOImpl<Seat, Integer> implements SeatDAO {
    public SeatDAOImpl(Class<Seat> persistentClass) {
        super(persistentClass);
    }

    public Optional<Seat> findSeat(Seat seat){
        try(Session session = HibernateConfig.getSessionFactory().openSession()){
            return session.createQuery(
                """
                   FROM Seat s
                   WHERE s.room.id = :roomId
                       AND s.seatColumn = :seatColumn
                       AND s.seatRow = :seatRow
                   """,
                    Seat.class)
                    .setParameter("roomId", seat.getRoom().getId())
                    .setParameter("seatColumn", seat.getSeatColumn())
                    .setParameter("seatRow", seat.getSeatRow())
                    .uniqueResultOptional();
        } catch (HibernateException ex){
            throw new DataAccessException("Failed to find " + Seat.class.getSimpleName() + ": " + ex.getMessage(), ex);
        }
    }
}
