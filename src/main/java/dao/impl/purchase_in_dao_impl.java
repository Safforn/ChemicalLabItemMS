package dao.impl;

import dao.purchase_in_dao;
import domain.Purchase_in_Warehouse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.util.List;

public class purchase_in_dao_impl implements purchase_in_dao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public boolean add(Purchase_in_Warehouse table) {
        String sql = "insert into purchase_in_warehouse(purchase_in_warehouse_id," +
                     "warehouse_id, purchase_in_order_id, purchase_order_id, date, type, notes)" +
                     "values(?,?,?,?,?,?,?)";
        try {
            template.update(sql,
                    table.getPurchase_in_warehouse_id(),
                    table.getWarehouse_id(),
                    table.getPurchase_in_order_id(),
                    table.getPurchase_order_id(),
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
    public List<String> getInOrder(String orderId) {
        String sql = "select purchase_in_order_id from purchase_in_warehouse where purchase_order_id = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(), orderId);
    }
}
