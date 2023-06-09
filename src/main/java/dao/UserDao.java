package dao;

import domain.Identity;
import domain.User;

import java.util.List;

public interface UserDao {
    User findByAccount(String account);
    void save(User user);
    User findByUidAndPassword(String uid, String password);
    Identity findByUserId(String uid);
//    void updateUser(User user);
    List<User> getMaxId();
    Identity findByIdentity(int identity);

    void saveId(Identity idtt);
}
