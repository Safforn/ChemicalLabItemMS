package Service.Impl;


import domain.Order;
import domain.User;
import org.junit.Test;
import service.service_temp.UserService;

public class UserServiceImplTest {
    UserService service = new UserServiceImpl();
    User u = new User("lisilisi", "李四","12345678","12345678914");

    @Test
    public void testRegist() {
        service.regist(u);
    }

    @Test
    public void testLogin() {
        service.login(u);
    }

    @Test
    public void testleaseAndBuy() {
        Order order = new Order();
        order.setGid("1");
        order.setUid("1234567890");
        order.setNum(7);
        System.out.println(service.leaseAndBuy(order));
    }
}
