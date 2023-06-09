package dao.impl;

import dao.warehouse_Dao;
import domain.Warehouse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.util.List;

public class warehouse_dao_impl implements warehouse_Dao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Warehouse> getMaxId() {
        String sql = "SELECT warehouse_id FROM warehouse ORDER BY warehouse_id DESC LIMIT 1";
        return template.query(sql, new BeanPropertyRowMapper<Warehouse>(Warehouse.class));
    }
}
