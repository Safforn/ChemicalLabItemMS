package dao.impl;

import dao.borrow_in_dao;
import domain.Borrow_in_Warehouse;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

public class borrow_in_dao_impl implements borrow_in_dao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public void add(Borrow_in_Warehouse table) {
        String sql = "insert into borrow_in_warehouse(borrow_in_warehouse_id, warehouse_id, get_or_borrow_order_id, date," +
                     "notes) values(?,?,?,?,?,?)";
        template.update(sql,
                table.getBorrow_in_warehouse_id(),
                table.getWarehouse_id(),
                table.getGet_or_borrow_order_id(),
                table.getDate(),
                table.getNotes());
    }
}
