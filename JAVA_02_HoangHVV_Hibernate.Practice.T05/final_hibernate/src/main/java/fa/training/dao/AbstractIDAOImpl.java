package fa.training.dao;

import fa.training.config.HibernateConfig;
import fa.training.exception.DataAccessException;
import jakarta.persistence.TypedQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractIDAOImpl<T, ID extends Serializable>
        implements IDAO<T, ID> {

    private final Class<T> entityClass;

    protected AbstractIDAOImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    // Helper method
    protected void executeInTransaction(Consumer<Session> action) {
        Transaction tx = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            action.accept(session);

            tx.commit();
        } catch (Exception ex) {

            if (tx != null) {
                tx.rollback();
            }

            throw new DataAccessException(
                    "Database operation failed: " + ex.getMessage(),
                    ex
            );
        }
    }

    protected <R> R execute(Function<Session, R> action) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return action.apply(session);
        } catch (Exception ex) {
            throw new DataAccessException(
                    "Database operation failed: " + ex.getMessage(),
                    ex
            );
        }
    }

    // CRUD
    @Override
    public void save(T entity) {
        executeInTransaction(session ->
                session.persist(entity)
        );
    }

    @Override
    public void update(T entity) {
        executeInTransaction(session ->
                session.merge(entity)
        );
    }

    @Override
    public void delete(T entity) {
        executeInTransaction(session -> {
            T managedEntity = session.merge(entity);
            session.remove(managedEntity);
        });
    }

    @Override
    public Optional<T> findById(ID id) {
        return execute(session ->
                Optional.ofNullable(
                        session.get(entityClass, id)
                )
        );
    }

    @Override
    public List<T> findAll() {
        return execute(session ->
                session.createQuery(
                                "FROM " + entityClass.getSimpleName(),
                                entityClass
                        )
                        .getResultList()
        );
    }

    @Override
    public <R> List<T> findByProperty(String propertyName, R value) {

        return execute(session -> {

            String hql =
                    "FROM " + entityClass.getSimpleName()
                            + " e WHERE e."
                            + propertyName
                            + " = :value";

            TypedQuery<T> query =
                    session.createQuery(hql, entityClass);

            query.setParameter("value", value);

            return query.getResultList();
        });
    }

    @Override
    public Optional<T> findByIdWithFetch(
            ID id,
            String... associationPaths) {

        return execute(session -> {

            String idProperty =
                    session.getMetamodel()
                            .entity(entityClass)
                            .getId(Object.class)
                            .getName();

            StringBuilder hql =
                    new StringBuilder(
                            "SELECT DISTINCT e FROM "
                                    + entityClass.getSimpleName()
                                    + " e"
                    );

            for (String path : associationPaths) {
                hql.append(" LEFT JOIN FETCH e.")
                        .append(path);
            }

            hql.append(" WHERE e.")
                    .append(idProperty)
                    .append(" = :id");

            return session.createQuery(
                            hql.toString(),
                            entityClass
                    )
                    .setParameter("id", id)
                    .getResultStream()
                    .findFirst();
        });
    }

    @Override
    public List<T> findAllWithFetch(
            String... associationPaths) {

        return execute(session -> {

            StringBuilder hql =
                    new StringBuilder(
                            "SELECT DISTINCT e FROM "
                                    + entityClass.getSimpleName()
                                    + " e"
                    );

            for (String path : associationPaths) {
                hql.append(" LEFT JOIN FETCH e.")
                        .append(path);
            }

            return session.createQuery(
                            hql.toString(),
                            entityClass
                    )
                    .getResultList();
        });
    }
}