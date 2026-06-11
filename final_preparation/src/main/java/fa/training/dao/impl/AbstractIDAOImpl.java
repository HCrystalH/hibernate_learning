package fa.training.dao.impl;

import fa.training.dao.IDAO;
import fa.training.exceptions.DataAccessException;
import fa.training.utils.HibernateUtil;
import jakarta.persistence.TypedQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class AbstractIDAOImpl<T, ID extends Serializable> implements IDAO<T, ID> {
    private final Class<T> entityClass;

    protected AbstractIDAOImpl(Class<T> persistentClass) {
        this.entityClass = persistentClass;
    }

    @Override
    public void save(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) transaction.rollback();
            throw new DataAccessException("Failed to save " + entityClass.getSimpleName() + ": " + ex.getMessage(), ex);
        }
    }

    @Override
    public void update(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) transaction.rollback();
            throw new DataAccessException("Failed to update " + entityClass.getSimpleName() + ": " + ex.getMessage(), ex);
        }
    }

    @Override
    public void delete(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            T managedEntity = session.merge(entity);
            session.remove(managedEntity);
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) transaction.rollback();
            throw new DataAccessException("Failed to delete " + entityClass.getSimpleName() + ": " + ex.getMessage(), ex);
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(entityClass, id));
        } catch (HibernateException ex) {
            throw new DataAccessException("Failed to find " + entityClass.getSimpleName() + ": " + ex.getMessage(), ex);
        }
    }

    @Override
    public List<T> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM " + entityClass.getName(), entityClass).getResultList();
        } catch (HibernateException ex) {
            throw new DataAccessException("Failed to find all " + entityClass.getSimpleName() + ": " + ex.getMessage(), ex);
        }
    }

    @Override
    public <R> List<T> findByProperty(String propertyName, R value) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM " + entityClass.getName() + " WHERE " + propertyName + " = :value";
            TypedQuery<T> query = session.createQuery(hql, entityClass);
            query.setParameter("value", value);
            return query.getResultList();
        } catch (HibernateException ex) {
            throw new DataAccessException("Failed to find by property " + propertyName + ": " + ex.getMessage(), ex);
        }
    }

    @Override
    public Optional<T> findByIdWithFetch(ID id, String... associationPaths) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("FROM " + entityClass.getName() + " e");
            for (String path : associationPaths) {
                hql.append(" LEFT JOIN FETCH e.").append(path);
            }
            hql.append(" WHERE e.id = :id");
            TypedQuery<T> query = session.createQuery(hql.toString(), entityClass);
            query.setParameter("id", id);
            return query.getResultStream().findFirst();
        } catch (HibernateException ex) {
            throw new DataAccessException("Failed to find " + entityClass.getSimpleName() + " with fetch: " + ex.getMessage(), ex);
        }
    }

    @Override
    public List<T> findAllWithFetch(String... associationPaths) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("FROM " + entityClass.getName() + " e");
            for (String path : associationPaths) {
                hql.append(" LEFT JOIN FETCH e.").append(path);
            }
            TypedQuery<T> query = session.createQuery(hql.toString(), entityClass);
            return query.getResultList();
        } catch (HibernateException ex) {
            throw new DataAccessException("Failed to find all " + entityClass.getSimpleName() + " with fetch: " + ex.getMessage(), ex);
        }
    }
}
