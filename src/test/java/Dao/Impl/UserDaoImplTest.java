package Dao.Impl;


import dao.UserDao;
import dao.impl.UserDaoImpl;
import domain.User;
import org.junit.Test;

public class UserDaoImplTest {
    UserDao dao = new UserDaoImpl();

    @Test
    public void testFindByUid() {
        User u = dao.findByUid("zhangsan");
        System.out.println(u);
    }

    @Test
    public void testSave() {
        User u = new User("lisilisi", "李四","12345678","12345678914");
        dao.save(u);
    }

    @Test
    public void testFindByUidAndPwd() {
        User u = dao.findByUidAndPassword("zhangsan", "12345678");
        System.out.println(u);
    }


}
