package model.dao.impl;

import model.entities.Room;
import model.config.HibernateConfig;
import model.dao.RoomDAO;
import model.exceptions.DataAccessException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class RoomImpl extends AbstractIDAOImpl<Room, Integer> implements RoomDAO {
    public RoomImpl(Class<Room> persistentClass) {
        super(persistentClass);
    }

    @Override
    public Optional<Room> findByName(String name) throws RuntimeException{
        try(Session session = HibernateConfig.getSessionFactory().openSession()){
            Room room = session.createQuery("FROM Room WHERE name = :name", Room.class)
                    .setParameter("name", name)
                    .uniqueResult();

            return Optional.ofNullable(room);
        } catch (Exception ex){
            throw new RuntimeException("Failed to find Room by name: " + name,ex);
        }
    }

    @Override
    public void insert(Room room){
        if(findByName(room.getName()).isPresent()){
            throw new IllegalStateException("Room already exists");
        }

        Transaction transaction = null;
        try(Session session = HibernateConfig.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            session.persist(room);

            transaction.commit();
        } catch (HibernateException ex){
            if(transaction != null) transaction.rollback();

            throw new DataAccessException("Failed to save " + Room.class.getSimpleName() + ": " + ex.getMessage(),ex);
        }
    }
}
