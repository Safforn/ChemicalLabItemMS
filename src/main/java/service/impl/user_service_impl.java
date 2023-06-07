package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import domain.Identity;
import domain.User;
import service.UserService;


public class user_service_impl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
        //1.根据用户名查询用户对象
        User u = userDao.findByAccount(user.getAccount());
        //判断 u 是否为 null
        if(u != null){
            //用户名存在，注册失败
            return false;
        }

        // TODO: 临时代码为新用户创建user_id
        user.setUser_id("0012");

        //2.保存用户信息
        userDao.save(user);
        return true;
    }

    /**
     *
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return userDao.findByUidAndPassword(user.getAccount(), user.getPassword());
    }

    @Override
    public Identity findIdentity(User user) {
        return userDao.findByUserId(user.getUser_id());
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

