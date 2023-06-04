package dao.dao_temp.impl;

import dao.dao_temp.AddtoWarehouseDao;
import domain.AddToWarehouse;
import domain.AddtoInfo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;
import java.util.List;

public class AddtoWarehouseDaoImpl implements AddtoWarehouseDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     *
     * @param addToWarehouse
     */
    @Override
    public void recving(AddToWarehouse addToWarehouse) {
        String sql="insert into add_to_warehouse(aid,uid,gid,time,num) value(?,?,?,?,?) ";
        template.update(sql,addToWarehouse.getAid(),
                addToWarehouse.getUid(),
                addToWarehouse.getGid(),
                addToWarehouse.getTime(),
                addToWarehouse.getNum()
        );
    }

    /**
     *
     * @return
     */
    @Override
    public List<AddtoInfo> searchAll() {
        String sql =" SELECT aid,goods.name,add_to_warehouse.num,`time` FROM add_to_warehouse JOIN goods ON add_to_warehouse.gid = goods.gid  order by `time` desc";
        return template.query(sql,new BeanPropertyRowMapper<AddtoInfo>(AddtoInfo.class));
    }
}
