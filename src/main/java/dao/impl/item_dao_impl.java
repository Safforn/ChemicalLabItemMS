package dao.impl;

import dao.item_dao;
import domain.Item;
import domain.Purchase_Requisition;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.util.List;

public class item_dao_impl implements item_dao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());


    /**
     * 根据 物品id查询物品信息
     * @param object_id
     * @return
     */
    @Override
    public Item findByObjectId(String object_id) {
        Item item = null;
        String sql = "select * from item where object_id = ? ";
        item = template.queryForObject(sql, new BeanPropertyRowMapper<Item>(Item.class), object_id);
        return item;
    }
}
