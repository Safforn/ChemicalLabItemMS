package dao;

import domain.User;

public interface UserDao {
//    User findByUid(String uid);
//    void save(User user);
    User findByUidAndPassword(String uid, String password);
//    void updateUser(User user);
}
