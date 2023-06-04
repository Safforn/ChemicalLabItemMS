package service.service_temp.impl;

import dao.dao_temp.OrderDao;
import dao.dao_temp.impl.OrderDaoImpl;
import domain.Order;
import service.service_temp.OrderService;

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
