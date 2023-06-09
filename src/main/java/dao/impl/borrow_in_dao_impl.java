package dao.impl;

import dao.borrow_in_dao;
import domain.Borrow_in_Warehouse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.util.List;

public class borrow_in_dao_impl implements borrow_in_dao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public boolean add(Borrow_in_Warehouse table) {
        String sql = "insert into borrow_in_warehouse(borrow_in_warehouse_id, warehouse_id, get_or_borrow_order_id, date," +
                     "notes) values(?,?,?,?,?,?)";
        try {
            template.update(sql,
                    table.getBorrow_in_warehouse_id(),
                    table.getWarehouse_id(),
                    table.getGet_or_borrow_order_id(),
                    table.getDate(),
                    table.getNotes());
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Borrow_in_Warehouse> getMaxId() {
        String sql = "SELECT borrow_in_warehouse_id FROM borrow_in_warehouse ORDER BY borrow_in_warehouse_id DESC LIMIT 1";
        return template.query(sql, new BeanPropertyRowMapper<Borrow_in_Warehouse>(Borrow_in_Warehouse.class));
    }
}
