package dao.impl;

import dao.UserDao;
import domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

//    /**
//     *
//     * @param uid
//     * @return
//     */
//    @Override
//    public User findByUid(String uid) {
//        User user = null;
//        try {
//            String sql = "select * from user where uid = ?";
//            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), uid);
//        } catch (Exception e) {
//        }
//        return user;
//    }
//
//    /**
//     *
//     * @param user
//     */
//    @Override
//    public void save(User user) {
//        String sql = "insert into user(uid,name,password,telephone) values(?,?,?,?)";
//        template.update(sql,
//                user.getUid(),
//                user.getName(),
//                user.getPassword(),
//                user.getTelephone());
//    }

    /**
     *
     * @param account 用户登录用的账号
     * @param password 用户登录用密码
     * @return 对应的User对象
     */
    @Override
    public User findByUidAndPassword(String account, String password) {
        User user = null;
        try {
            String sql = "select * from user where account = ? and password = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), account, password);
        } catch (Exception e) {
        }
        return user;
    }

//    @Override
//    public void updateUser(User user) {
//        String sql = "update user set name=?, telephone=? where uid=?";
//        template.update(sql, user.getName(), user.getTelephone(), user.getUid());
//    }

}
