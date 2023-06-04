package Dao;

import dao.dao_temp.OrderDao;
import dao.dao_temp.impl.OrderDaoImpl;
import domain.Order;
import org.junit.Test;
import util.UuidUtil;

class OrderDaoTest {
    private OrderDao orderDao = new OrderDaoImpl();

    @Test
    void insertOrder() {
        Order order = new Order();
        order.setOid(UuidUtil.getUuid());
        order.setUid("1234567890");
        order.setGid("1");
        System.out.println(orderDao.insertOrder(order));
    }
}