package model.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface IDAO<T,ID extends Serializable> {
    void insert(T entity);
    void update(T entity);
    void delete(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
}
