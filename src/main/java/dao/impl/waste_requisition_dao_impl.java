package dao.impl;

import dao.waste_requisition_dao;
import domain.Purchase_Requisition;
import domain.Waste_Requisition;
import domain.get_or_borrow_Requisition;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.util.List;

public class waste_requisition_dao_impl implements waste_requisition_dao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public void createTable(Waste_Requisition table) {
        String sql = "insert into waste_requisition(waste_requisition_id, " +
                "waste_order_id, requisition_user_id, waste_reason, position, " +
                "state, requisition_date) " +
                "values (?, ?, ?, ?, ?, ?, ?)";
        template.update(sql,
                table.getWaste_requisition_id(),
                table.getWaste_order_id(),
                table.getRequisition_user_id(),
                table.getWaste_reason(),
                table.getPosition(),
                table.getState(),
                table.getRequisition_date());
    }

    @Override
    public void updateTable(Waste_Requisition table) {
        String sql = "update waste_requisition set waste_requisition_id = ?, " +
                "waste_order_id = ?, requisition_user_id = ?, waste_reason = ?, position = ?, " +
                "state = ?, requisition_date = ?, , approval_user_id = ?, approval_opinions = ?, " +
                "approval_date = ?)";
        template.update(sql,
                table.getWaste_requisition_id(),
                table.getWaste_order_id(),
                table.getRequisition_user_id(),
                table.getWaste_reason(),
                table.getPosition(),
                table.getState(),
                table.getRequisition_date(),
                table.getApproval_user_id(),
                table.getApproval_opinions(),
                table.getApproval_date());
    }

    @Override
    public void deleteTable(String tableId) {
        String sql = "delete from waste_requisition where waste_requisition_id = ?";
        template.update(sql, tableId);
    }

    @Override
    public Waste_Requisition searchTableById(String tableId) {
        Waste_Requisition table = null;
        String sql = "select * from waste_requisition where waste_requisition_id = ?";
        try{
            table = template.queryForObject(sql, new BeanPropertyRowMapper<Waste_Requisition>(Waste_Requisition.class), tableId);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    @Override
    public List<Waste_Requisition> searchTableByUser(String userId) {
        String sql = "select * from waste_requisition where applicant_user_id = ?";
        return template.query(sql, new BeanPropertyRowMapper<Waste_Requisition>(Waste_Requisition.class), userId);
    }

    @Override
    public List<Waste_Requisition> searchTableByState(int state) {
        String sql = "select * from waste_requisition where state = ?";
        return template.query(sql, new BeanPropertyRowMapper<Waste_Requisition>(Waste_Requisition.class), state);
    }
}
