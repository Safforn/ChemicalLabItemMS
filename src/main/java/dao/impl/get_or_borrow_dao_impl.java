package dao.impl;

import dao.get_or_borrow_dao;
import domain.get_or_borrow_Requisition;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.util.List;

public class get_or_borrow_dao_impl implements get_or_borrow_dao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public void createTable(get_or_borrow_Requisition table) {
        String sql = "insert into get_or_borrow_requisition(get_or_borrow_requisition_id, " +
                                 "get_or_borrow_order_id, approval_user_id, purpose, requisition_date, " +
                                 "borrow_date, return_date,state, type) " +
                                 "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql,
                table.getGet_or_borrow_requisition_id(),
                table.getGet_or_borrow_order_id(),
                table.getApproval_user_id(),
                table.getPurpose(),
                table.getRequisition_date(),
                table.getBorrow_date(),
                table.getReturn_date(),
                table.getState(),
                table.getType());
    }

    @Override
    public void updateTable(get_or_borrow_Requisition table) {
        String sql = "update get_or_borrow_requisition set get_or_borrow_requisition_id = ?, " +
                "get_or_borrow_order_id = ?, approval_user_id = ?, purpose = ?, requisition_date = ?, " +
                "borrow_date = ?, return_date = ?,state = ?, type = ?," +
                "applicant_user_id = ?, approval_opinions = ?, approval_date = ?) ";
        template.update(sql,
                table.getGet_or_borrow_requisition_id(),
                table.getGet_or_borrow_order_id(),
                table.getApproval_user_id(),
                table.getPurpose(),
                table.getRequisition_date(),
                table.getBorrow_date(),
                table.getReturn_date(),
                table.getState(),
                table.getType(),
                table.getApplicant_user_id(),
                table.getApproval_opinions(),
                table.getApproval_date());
    }

    @Override
    public void deleteTable(String tableId) {
        String sql = "DELETE from get_or_borrow_requisition where get_or_borrow_requisition_id = ?";
        template.update(sql, tableId);
    }

    @Override
    public void submitTable(String tableId) {
        String sql = "update get_or_borrow_requisition set state = 1 where get_or_borrow_requisition_id = ?";
        template.update(sql, tableId);
    }

    @Override
    public get_or_borrow_Requisition searchTableById(String tableId) {
        get_or_borrow_Requisition table = null;
        String sql = "select * from get_or_borrow_requisition where get_or_borrow_requisition_id = ?";
        try{
            table = template.queryForObject(sql, new BeanPropertyRowMapper<get_or_borrow_Requisition>(get_or_borrow_Requisition.class), tableId);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    @Override
    public List<get_or_borrow_Requisition> searchTableByUser(String userId) {
        String sql = "select * from get_or_borrow_requisition where approval_user_id = ?";
        return template.query(sql, new BeanPropertyRowMapper<get_or_borrow_Requisition>(get_or_borrow_Requisition.class), userId);
    }

    @Override
    public List<get_or_borrow_Requisition> searchTableByState(int state) {
        String sql = "select * from get_or_borrow_requisition where state = ?";
        return template.query(sql, new BeanPropertyRowMapper<get_or_borrow_Requisition>(get_or_borrow_Requisition.class), state);
    }
}
