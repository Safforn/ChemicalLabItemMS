package service;

import domain.Goods;
import domain.Order;

import java.util.List;

public interface AdminService {
    List<Goods> searchGoods();
    boolean exWarehouse(Order order, String uid);
    boolean addToWarehouseByOrder(Order order, String uid);
    boolean cancelOrder(Order order);
    List<Order> searchOrderByType(int type);
}
