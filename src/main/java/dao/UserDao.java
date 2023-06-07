package dao;

import domain.User;

public interface UserDao {
    User findByAccount(String account);
    void save(User user);
    User findByUidAndPassword(String uid, String password);
//    void updateUser(User user);
}
