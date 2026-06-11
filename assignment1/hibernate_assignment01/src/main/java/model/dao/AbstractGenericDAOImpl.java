package model.dao;

import model.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class AbstractGenericDAOImpl<T,ID extends Serializable> implements GenericDAO<T,ID> {
    private final Class<T> entityClass;

    protected AbstractGenericDAOImpl(Class<T> persistentClass) {
        this.entityClass = persistentClass;
    }

    @Override
    public void save(T entity) throws RuntimeException{
        // Implementation for saving an entity to the database using Hibernate
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save entity " + entity + ": " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            T entity = session.get(entityClass, id);
            return Optional.ofNullable(entity);
        }
    }

    @Override
    public List<T> findAll() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("from " + entityClass.getName(), entityClass).getResultList();
        }
    }

    @Override
    public void update(T entity) throws RuntimeException {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to update entity " + entity, e);
        }
    }

    @Override
    public void delete(T entity) throws RuntimeException {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            if(entity!=null) session.remove(entity);

            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to delete entity", e);
        }
    }
}
