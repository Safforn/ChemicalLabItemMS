package dao.impl;

import dao.other_in_dao;
import domain.Other_in_Warehouse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.util.List;

public class other_in_dao_impl implements other_in_dao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public boolean add(Other_in_Warehouse table) {
        String sql = "insert into other_in_warehouse(other_in_warehouse_id, other_in_order_id, warehouse_id, date, type, notes)" +
                     "values(?, ?, ?, ?, ?, ?)";
        try {
            template.update(sql,
                    table.getOther_in_warehouse_id(),
                    table.getOther_in_order_id(),
                    table.getWarehouse_id(),
                    table.getDate(),
                    table.getType(),
                    table.getNotes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Other_in_Warehouse> search() {
        String sql = "select * from other_in_warehouse";
        return template.query(sql, new BeanPropertyRowMapper<Other_in_Warehouse>(Other_in_Warehouse.class));
    }
}
