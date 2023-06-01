package service;

import domain.Order;

import java.util.List;

public interface OrderService {
    List<Order> searchOrderByType(int type);
}
