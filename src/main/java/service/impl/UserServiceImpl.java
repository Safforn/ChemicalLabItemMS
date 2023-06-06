package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import domain.User;
import service.UserService;
import util.UuidUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    /**
     * 注册用户
     * @param user
     * @return
     */
//    @Override
//    public boolean regist(User user) {
//        //1.根据用户名查询用户对象
//        User u = userDao.findByUid(user.getUid());
//        //判断 u 是否为 null
//        if(u != null){
//            //用户名存在，注册失败
//            return false;
//        }
//        //2.保存用户信息
//        userDao.save(user);
//        return true;
//    }

    /**
     *
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return userDao.findByUidAndPassword(user.getAccount(), user.getPassword());
    }

//    @Override
//    public void updateUser(User user) {
//        userDao.updateUser(user);
//    }
//
//    @Override
//    public User loginByUid(String uid) {
//        return userDao.findByUid(uid);
//    }

}
