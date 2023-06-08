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


    @Override
    public boolean add(Item item) {
        String sql = "insert into item(object_id, name, specification, quantity, unit, classification, price, experation_time, upper_limit, lower_limit, notes)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            template.update(sql,
                    item.getObject_id(),
                    item.getName(),
                    item.getSpecification(),
                    item.getQuantity(),
                    item.getUnit(),
                    item.getClassification(),
                    item.getPrice(),
                    item.getExpiration_time(),
                    item.getUpper_limit(),
                    item.getLower_limit(),
                    item.getNotes());
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean changeNum(String id, int num) {
        String sql = "update item set num = num + ? where object_id = ?";
        try {
            template.update(sql, num, id);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Item> search() {
        String sql = "select * from item";
        return template.query(sql, new BeanPropertyRowMapper<>());
    }

    /**
     * 根据 物品id查询物品信息
     * @param object_id
     * @return
     */
    @Override
    public Item findByObjectId(String object_id) {
        String sql = "select * from item where object_id = ? ";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Item>(Item.class), object_id);
    }
}
