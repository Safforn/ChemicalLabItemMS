package dao;

import domain.Identity;
import domain.User;

public interface UserDao {
    User findByAccount(String account);
    void save(User user);
    User findByUidAndPassword(String uid, String password);
    Identity findByUserId(String uid);
//    void updateUser(User user);
}
