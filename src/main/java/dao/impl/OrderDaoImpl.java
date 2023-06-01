package dao.impl;

import dao.OrderDao;
import domain.Order;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Order> searchOrderByAdmin(int type) {
        String sql = "select * from `order` where type = ? order by starttime desc";
        return template.query(sql, new BeanPropertyRowMapper<Order>(Order.class), type);
    }

    @Override
    public Boolean insertOrder(Order order) {
        String sql = "insert into `order`(oid, gid, uid, type, expressid, location, username, usertelephone, starttime, renttime, num) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            template.update(sql, order.getOid(),
                    order.getGid(),
                    order.getUid(),
                    order.getType(),
                    order.getExpressid(),
                    order.getLocation(),
                    order.getUsername(),
                    order.getUsertelephone(),
                    order.getStarttime(),
                    order.getRenttime(),
                    order.getNum());
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    @Override
    public List<Order> searchByType(int type) {
        String sql = "select * from `order` where type = ? order by starttime desc";
        return template.query(sql, new BeanPropertyRowMapper<Order>(Order.class), type);
    }

    @Override
    public List<Order> searchByTypeAndUser(int type, String uid) {
        String sql = "select * from `order` where type = ? and uid = ? order by starttime desc";
        System.out.println(sql + " " + type + " " + uid);
        return template.query(sql, new BeanPropertyRowMapper<Order>(Order.class), type, uid);
    }

}
