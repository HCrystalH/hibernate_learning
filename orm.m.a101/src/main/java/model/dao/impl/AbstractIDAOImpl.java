package model.dao.impl;

import model.dao.IDAO;
import model.exceptions.DataAccessException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import model.config.HibernateConfig;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class AbstractIDAOImpl<T,ID extends Serializable> implements IDAO<T,ID> {
    private final Class<T> entityClass;

    protected AbstractIDAOImpl(Class<T> persistentClass) {
        this.entityClass = persistentClass;
    }

    @Override
    public void insert(T entity) {
        Transaction transaction = null;

        try(Session session = HibernateConfig.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (HibernateException ex){
            if(transaction != null) transaction.rollback();

            throw new DataAccessException("Failed to save " + entityClass.getSimpleName() + ": " + ex.getMessage(),ex);
        }
    }

    @Override
    public void update(T entity) {
        Transaction transaction = null;
        try(Session session = HibernateConfig.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        } catch (HibernateException ex){
            if(transaction != null) transaction.rollback();
            throw new DataAccessException("Failed to update " + entityClass.getSimpleName() + ": " + ex.getMessage(), ex);
        }
    }

    @Override
    public void delete(T entity) {
        Transaction transaction = null;
        try(Session session = HibernateConfig.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            // For detached entities, we need to merge them first to get a managed instance before deletion
            T managedEntity = session.merge(entity);
            session.remove(managedEntity);

            transaction.commit();
        } catch (HibernateException ex){
            if(transaction != null) transaction.rollback();
            throw new DataAccessException("Failed to delete " + entityClass.getSimpleName() + ": " + ex.getMessage(), ex);
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        try(Session session = HibernateConfig.getSessionFactory().openSession()){
            return Optional.ofNullable(session.get(entityClass,id));
        } catch (HibernateException ex){
            throw new DataAccessException("Failed to find " + entityClass.getSimpleName() + ": " + ex.getMessage(), ex);
        }
    }

    @Override
    public List<T> findAll() {
        try(Session session = HibernateConfig.getSessionFactory().openSession()){
            return session.createQuery("FROM " + entityClass.getName(),entityClass).getResultList();
        } catch (HibernateException ex){
            throw new DataAccessException("Failed to find all " + entityClass.getSimpleName() + ": " + ex.getMessage(), ex);
        }
    }
}
