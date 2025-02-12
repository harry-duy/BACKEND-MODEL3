package service;

import java.util.List;

public interface IService<T> {
    List<T> getAll();
    void remove(int id);
    void update(int id, T t);
    T findById(int id);
    List<T> findByName(String name);
}
