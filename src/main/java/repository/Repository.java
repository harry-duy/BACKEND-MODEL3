package repository;

import java.util.List;

public interface Repository<T> {
    List<T> findAll();
    T findById(int id);
    boolean save(T entity);
    boolean update(T entity);
    boolean delete(int id);
}