package dao.impl;

import dao.ExwarehouseDao;
import domain.ExInfo;
import domain.ExWarehouse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.util.List;

public class ExwarehouseDaoImpl implements ExwarehouseDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     *
     * @param exWarehouse
     */
    @Override
    public void exhausting(ExWarehouse exWarehouse) {
        String sql = "insert into ex_warehouse(eid,oid,uid) values(?,?,?)";
        template.update(sql,exWarehouse.getEid(),
                exWarehouse.getOid(),
                exWarehouse.getUid()
        );
    }

    /**
     *
     * @return
     */
    @Override
    public List<ExInfo> searchAll() {
        String sql = "SELECT eid,goods.name,order.num,ex_warehouse.oid,order.starttime,ex_warehouse.uid FROM `order` " +
                "JOIN ex_warehouse ON ex_warehouse.oid=`order`.oid JOIN goods ON goods.gid=order.gid  order by order.starttime desc";

        return template.query(sql,new BeanPropertyRowMapper<ExInfo>(ExInfo.class));
    }
}
