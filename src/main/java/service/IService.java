package service;

import java.util.List;

public interface IService<T> {
    List<T> getAll();
    void add(T t);
    void update(int id, T t);
    void update(T t);
    void remove(int id);
    T findById(int id);
    List<T> findByName(String name);
}
