package dao.impl;

import dao.object_entry_dao;
import domain.Object_Entry;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.util.List;

public class object_entry_dao_impl implements object_entry_dao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public boolean addEntry(List<Object_Entry> lists, String orderId) {
        String sql = "insert into object_entry(object_entry_id, order_id, object_id, num)" +
                     "values(?, ?, ?, ?)";
        for (Object_Entry list : lists) {
            try {
                template.update(sql,
                        list.getObject_entry_id(),
                        orderId,
                        list.getObject_id(),
                        list.getNum());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addOne(Object_Entry oe, String orderId) {
        String sql = "insert into boject_entry(object_entry_id, order_id, object_id, num)" +
                     "values(?, ?, ?, ?)";
        try {
            template.update(sql,
                    oe.getObject_entry_id(), orderId, oe.getObject_id(), oe.getNum());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteEntryById(String id) {
        String sql = "delete from object_entry where object_entry_id = ?";
        try {
            template.update(sql, id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteEntryByOrder(String orderId) {
        String sql = "delete from object_entry where order_id = ?";
        try {
            template.update(sql, orderId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int findOne(String orderId, String objectId) {
        String sql = "select count(*) from object_entry where order_id = ? and object_id = ?";
        return template.queryForInt(sql, orderId, objectId);
    }

    @Override
    public boolean updateOne(String orderId, String objectId, int num) {
        String sql = "update object_entry set quantity = quantity + ? where order_id = ? and object_id = ?";
        try {
            template.update(sql, num, orderId, objectId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<Object_Entry> search(String orderId) {
        String sql = "select * from object_entry where order_id = ?";
        return template.query(sql, new BeanPropertyRowMapper<Object_Entry>(Object_Entry.class), orderId);
    }
}
