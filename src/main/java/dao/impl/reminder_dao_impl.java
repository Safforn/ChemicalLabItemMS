package dao.impl;

import dao.reminder_dao;
import domain.Reminder;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

public class reminder_dao_impl implements reminder_dao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public void add(Reminder reminder) {
        String sql = "insert into reminder(id, get_or_borrow_requisition_id) values(?, ?)";
        template.update(sql, reminder.getId(), reminder.getGet_or_borrow_requisition_id());
    }

    @Override
    public void delete(String id) {
        String sql = "delete from reminder where id = ?";
        template.update(sql, id);
    }
}
