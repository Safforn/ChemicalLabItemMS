package dao.impl;

import dao.purchase_requisition_dao;
import domain.Purchase_Requisition;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.util.List;

public class purchase_requisition_dao_impl implements purchase_requisition_dao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public boolean createTable(Purchase_Requisition table) {
        String sql = "insert into purchase_requisition(purchase_requisition_id," +
                                                "purchase_order_id, " +
                                                "requisition_user_id, " +
                                                "state, purpose, requisition_date)" +
                     "values(?, ?, ?, ?, ?, ?)";
        try {
            template.update(sql,
                    table.getPurchase_requisition_id(),
                    table.getPurchase_order_id(),
                    table.getRequisition_user_id(),
                    table.getState(),
                    table.getPurpose(),
                    table.getRequisition_date());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateTable(Purchase_Requisition table) {
        String sql = "update purchase_requisition set purchase_requisition_id = ?," +
                "purchase_order_id = ?, requisition_user_id = ?, state = ?, purpose = ?, requisition_date = ?," +
                "approval_user_id = ?, approval_opinions = ?, approval_date = ?";
        try {
            template.update(sql,
                    table.getPurchase_requisition_id(),
                    table.getPurchase_order_id(),
                    table.getRequisition_user_id(),
                    table.getState(),
                    table.getPurpose(),
                    table.getRequisition_date(),
                    table.getApproval_user_id(),
                    table.getApproval_opinions(),
                    table.getApproval_date());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean deleteTable(String tableId) {
        String sql = "delete from purchase_requisition where purchase_requisition_id = ?";
        try {
            template.update(sql, tableId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Purchase_Requisition searchTableById(String tableId) {
        Purchase_Requisition table = null;
        String sql = "select * from purchase_requisition where purchase_requisition_id = ?";
        try{
            table = template.queryForObject(sql, new BeanPropertyRowMapper<Purchase_Requisition>(Purchase_Requisition.class), tableId);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    @Override
    public List<Purchase_Requisition> searchTableByUser(String userId) {
        String sql = "select * from purchase_requisition where requisition_user_id = ?";
        return template.query(sql, new BeanPropertyRowMapper<Purchase_Requisition>(Purchase_Requisition.class), userId);
    }

    @Override
    public List<Purchase_Requisition> searchTableByState(int state) {
        String sql = "select * from purchase_requisition where state = ?";
        return template.query(sql, new BeanPropertyRowMapper<Purchase_Requisition>(Purchase_Requisition.class), state);
    }
}
