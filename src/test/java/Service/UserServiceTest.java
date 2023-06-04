package Service;

import org.junit.jupiter.api.Test;
import service.service_temp.UserService;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    UserService userService = new UserServiceImpl();
    @Test
    void regist() {
    }

    @Test
    void login() {
    }

    @Test
    void searchOrder() {
    }

    @Test
    void searchGoods() {
    }

    @Test
    void leaseAndBuy() {
    }

    @Test
    void searchOrderByType() {
        System.out.println(userService.searchOrderByType(1, "1234567890"));
    }
}