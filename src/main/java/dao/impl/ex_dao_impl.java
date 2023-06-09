package dao.impl;

import dao.ex_dao;
import domain.Ex_Warehouse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.util.List;

public class ex_dao_impl implements ex_dao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public boolean add(Ex_Warehouse ex_warehouse) {
        String sql = "insert into ex_warehouse(ex_warehouse_id, ex_order_id, warehouse_id, requisition_id, date, notes)" +
                     "values(?, ?, ?, ?, ?, ?)";
        try {
            template.update(sql,
                    ex_warehouse.getEx_warehouse_id(),
                    ex_warehouse.getEx_order_id(),
                    ex_warehouse.getWarehouse_id(),
                    ex_warehouse.getRequisition_id(),
                    ex_warehouse.getDate(),
                    ex_warehouse.getNotes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Ex_Warehouse> search() {
        String sql = "select * from ex_warehouse";
        return template.query(sql, new BeanPropertyRowMapper<>());
    }
    @Override
    public List<Ex_Warehouse> getMaxId() {
        String sql = "SELECT ex_warehouse_id FROM ex_warehouse ORDER BY ex_warehouse_id DESC LIMIT 1";
        return template.query(sql, new BeanPropertyRowMapper<Ex_Warehouse>(Ex_Warehouse.class));
    }
}
