package service.impl;

import service.IService;

import java.util.List;

public class OrderService implements IService {

    @Override
    public List getAll() {
        return List.of();
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void update(int id, Object o) {

    }

    @Override
    public Object findById(int id) {
        return null;
    }

    @Override
    public List findByName(String name) {
        return List.of();
    }
}
