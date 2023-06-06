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
    public void addEntry(List<Object_Entry> lists, String orderId) {
        String sql = "insert into object_entry(object_entry_id, order_id, object_id, num)" +
                     "values(?, ?, ?, ?)";
        for (Object_Entry list : lists) {
            template.update(sql,
                    list.getObject_entry_id(),
                    orderId,
                    list.getObject_id(),
                    list.getNum());
        }
    }

    @Override
    public void deleteEntryById(String id) {
        String sql = "delete from object_entry where object_entry_id = ?";
        template.update(sql, id);
    }

    @Override
    public void deleteEntryByOrder(String orderId) {
        String sql = "delete from object_entry where order_id = ?";
        template.update(sql, orderId);
    }

    @Override
    public List<Object_Entry> search(String orderId) {
        String sql = "select * from object_entry where order_id = ?";
        return template.query(sql, new BeanPropertyRowMapper<Object_Entry>(Object_Entry.class), orderId);
    }
}
