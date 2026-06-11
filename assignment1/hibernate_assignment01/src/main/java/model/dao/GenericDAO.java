package model.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, ID extends Serializable> {
    Optional<T> findById(ID id);
    void save(T entity) throws RuntimeException;
    void update(T entity) throws RuntimeException;
    void delete(T entity) throws RuntimeException;
    List<T> findAll();
}
