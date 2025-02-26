package service.impl;

import model.Order;
import repository.order.OrderRepository;
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
    public void update(int id, Order o) {

    }

    @Override
    public Order findById(int id) {
        return OrderRepository.findById(id); // Gọi phương thức chính xác
    }


    @Override
    public List<Order> findByName(String name) {
        return List.of();
    }
}
