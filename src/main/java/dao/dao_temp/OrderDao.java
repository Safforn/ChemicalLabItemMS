package dao.dao_temp;

import domain.Order;

import java.util.List;

public interface OrderDao {
    List<Order> searchOrderByAdmin(int type);
    Boolean insertOrder(Order order);
    List<Order> searchByType(int type);
    List<Order> searchByTypeAndUser(int type, String uid);
}
