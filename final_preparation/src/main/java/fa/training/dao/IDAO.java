package fa.training.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface IDAO<T, ID extends Serializable> {
    void save(T entity);
    void update(T entity);
    void delete(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();

    <R> List<T> findByProperty(String propertyName, R value);
    Optional<T> findByIdWithFetch(ID id, String... associationPaths);
    List<T> findAllWithFetch(String... associationPaths);
}
