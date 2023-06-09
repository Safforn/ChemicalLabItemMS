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
    public boolean createTable(get_or_borrow_Requisition table) {
        String sql = "insert into get_or_borrow_requisition(get_or_borrow_requisition_id, " +
                                 "get_or_borrow_order_id, applicant_user_id, purpose, requisition_date, " +
                                 "borrow_date, return_date,state, type) " +
                                 "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            template.update(sql,
                    table.getGet_or_borrow_requisition_id(),
                    table.getGet_or_borrow_order_id(),
                    table.getApplicant_user_id(),
                    table.getPurpose(),
                    table.getRequisition_date(),  // 格式化
                    table.getBorrow_date(),
                    table.getReturn_date(),
                    table.getState(),
                    table.getType());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateTable(get_or_borrow_Requisition table) {
        String sql = "update get_or_borrow_requisition set " +
                "applicant_user_id = ?, purpose = ?, requisition_date = ?, " +
                "borrow_date = ?, return_date = ?,state = ?, type = ?," +
                "applicant_user_id = ?, approval_user_id = ?, approval_opinions = ?, " +
                "approval_date = ? where get_or_borrow_requisition_id = ?";
        try {
            template.update(sql,
                    table.getApplicant_user_id(),
                    table.getPurpose(),
                    table.getRequisition_date(),
                    table.getBorrow_date(),
                    table.getReturn_date(),
                    table.getState(),
                    table.getType(),
                    table.getApplicant_user_id(),
                    table.getApproval_user_id(),
                    table.getApproval_opinions(),
                    table.getApproval_date(),
                    table.getGet_or_borrow_requisition_id());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean deleteTable(String tableId) {
        String sql = "DELETE from get_or_borrow_requisition where get_or_borrow_requisition_id = ?";
        try {
            template.update(sql, tableId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    @Override
//    public void submitTable(String tableId) {
//        String sql = "update get_or_borrow_requisition set state = 1 where get_or_borrow_requisition_id = ?";
//        template.update(sql, tableId);
//    }

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
    public get_or_borrow_Requisition searchTableByOrderId(String orderId) {
        get_or_borrow_Requisition table = null;
        String sql = "select * from get_or_borrow_requisition where get_or_borrow_order_id = ?";
        try{
            table = template.queryForObject(sql, new BeanPropertyRowMapper<get_or_borrow_Requisition>(get_or_borrow_Requisition.class), orderId);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    @Override
    public List<get_or_borrow_Requisition> searchTableByUser(String userId) {
        String sql = "select * from get_or_borrow_requisition where applicant_user_id = ?";
        return template.query(sql, new BeanPropertyRowMapper<get_or_borrow_Requisition>(get_or_borrow_Requisition.class), userId);
    }

    @Override
    public List<get_or_borrow_Requisition> searchTableByState(int state) {
        String sql = "select * from get_or_borrow_requisition where state = ?";
        return template.query(sql, new BeanPropertyRowMapper<get_or_borrow_Requisition>(get_or_borrow_Requisition.class), state);
    }

    @Override
    public List<get_or_borrow_Requisition> searchBorrowUnreturn() {
        String sql = "select * from get_or_borrow_requisition where type = 1 and state = 4";
        return template.query(sql, new BeanPropertyRowMapper<get_or_borrow_Requisition>(get_or_borrow_Requisition.class));
    }

    @Override
    public List<get_or_borrow_Requisition> getMaxId() {
        String sql = "SELECT get_or_borrow_requisition_id FROM get_or_borrow_requisition ORDER BY get_or_borrow_requisition_id DESC LIMIT 1";
        return template.query(sql, new BeanPropertyRowMapper<get_or_borrow_Requisition>(get_or_borrow_Requisition.class));
    }
}
