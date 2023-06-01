package Service;

import domain.Order;
import org.junit.Test;
import service.AdminService;
import service.impl.AdminServiceImpl;
import util.UuidUtil;

public class AdminServiceTest {
    private AdminService adminService = new AdminServiceImpl();

    @Test
    public void exWarehouse() {
        Order order = new Order();
        order.setOid("1");
        order.setGid("1");
        order.setUid("1234567890");
        order.setNum(7);
        System.out.println(adminService.exWarehouse(order, "1234567890"));
    }
    @Test
    public void addToWarehouseByOrder() {
        Order order = new Order();
        order.setOid("1");
        order.setGid("1");
        order.setUid("1234567890");
        order.setNum(100);
        System.out.println(adminService.addToWarehouseByOrder(order, "1234567890"));
    }
    @Test
    public void cancelOrder() {
        Order order = new Order();
        order.setOid("1");
        order.setGid("1");
        order.setUid("1234567890");
        order.setNum(7);
        System.out.println(adminService.cancelOrder(order));
    }
    @Test
    public void searchByType() {
        System.out.println(adminService.searchOrderByType(1));
    }
}