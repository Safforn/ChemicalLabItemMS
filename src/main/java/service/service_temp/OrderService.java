package service.service_temp;

import domain.Order;

import java.util.List;

public interface OrderService {
    List<Order> searchOrderByType(int type);
}
