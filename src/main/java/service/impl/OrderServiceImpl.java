package service.impl;

import dao.OrderDao;
import dao.impl.OrderDaoImpl;
import domain.Order;
import service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    OrderDao orderDao = new OrderDaoImpl();

    /**
     *
     * @param type
     * @return
     */
    @Override
    public List<Order> searchOrderByType(int type) {
        return orderDao.searchOrderByAdmin(type);
    }
}
