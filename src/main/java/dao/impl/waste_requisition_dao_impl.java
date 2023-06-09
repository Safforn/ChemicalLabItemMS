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
    public boolean createTable(Waste_Requisition table) {
        String sql = "insert into waste_requisition(waste_requisition_id, " +
                "waste_order_id, requisition_user_id, waste_reason, position, " +
                "state, requisition_date) " +
                "values (?, ?, ?, ?, ?, ?, ?)";
        try {
            template.update(sql,
                    table.getWaste_requisition_id(),
                    table.getWaste_order_id(),
                    table.getRequisition_user_id(),
                    table.getWaste_reason(),
                    table.getPosition(),
                    table.getState(),
                    table.getRequisition_date());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateTable(Waste_Requisition table) {
        String sql = "update waste_requisition set " +
                "requisition_user_id = ?, waste_reason = ?, position = ?, " +
                "state = ?, requisition_date = ?, , approval_user_id = ?, approval_opinions = ?, " +
                "approval_date = ? where waste_requisition_id = ?";
        try {
            template.update(sql,
                    table.getRequisition_user_id(),
                    table.getWaste_reason(),
                    table.getPosition(),
                    table.getState(),
                    table.getRequisition_date(),
                    table.getApproval_user_id(),
                    table.getApproval_opinions(),
                    table.getApproval_date(),
                    table.getWaste_requisition_id());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteTable(String tableId) {
        String sql = "delete from waste_requisition where waste_requisition_id = ?";
        try {
            template.update(sql, tableId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
        String sql = "select * from waste_requisition where requisition_user_id = ?";
        return template.query(sql, new BeanPropertyRowMapper<Waste_Requisition>(Waste_Requisition.class), userId);
    }

    @Override
    public List<Waste_Requisition> searchTableByState(int state) {
        String sql = "select * from waste_requisition where state = ?";
        return template.query(sql, new BeanPropertyRowMapper<Waste_Requisition>(Waste_Requisition.class), state);
    }

    @Override
    public boolean updateByApprove(Waste_Requisition table) {
        //purchase_requisition_id=PR-0000002&state=2&approval_user_id=0005&approval_opinions=朕允了
        String sql = "update purchase_requisition set state = ?, approval_user_id = ?, approval_opinions = ?, approval_date = ? where purchase_requisition_id = ?";
        try {
            template.update(sql,
                    table.getState(),
                    table.getApproval_user_id(),
                    table.getApproval_opinions(),
                    table.getApproval_date(),
                    table.getWaste_requisition_id()
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
