package dao.impl;

import dao.GoodsDao;
import domain.Goods;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;
import util.UuidUtil;

import java.util.List;

public class GoodsDaoImpl implements GoodsDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     *
     * @return
     */
    @Override
    public List<Goods> searchAll() {
        //查询所有货物
        String sql = "select * from goods where num>0";
        return template.query(sql,new BeanPropertyRowMapper<Goods>(Goods.class));
    }

    /**
     *
     * @param gid
     * @return
     */
    @Override
    public Goods searchByGid(String gid) {
        //通过gid查询货物
        Goods goods = null;
        String sql = "select * from goods where num>0 and gid=?";
        System.out.println(gid);
        try{
            goods = template.queryForObject(sql,new BeanPropertyRowMapper<Goods>(Goods.class),gid);
        } catch (Exception e){
            e.printStackTrace();
        }
        return goods;
    }

    /**
     * 新增货物时检查是否存在过(数量可能为0)
     * @param gid
     * @return Goods
     */
    @Override
    public Goods checkExistByGid(String gid) {
        //通过gid查询货物
        Goods goods = null;
        String sql = "select * from goods where gid=?";
        System.out.println(gid);
        try{
            goods = template.queryForObject(sql,new BeanPropertyRowMapper<Goods>(Goods.class),gid);
        } catch (Exception e){
            e.printStackTrace();
        }
        return goods;
    }

    /**
     *
     * @param gid
     * @param num
     */
    @Override
    public void updateGoodsNumber(String gid, int num) {
        //更新货物的数量
        String sql = "update goods set num=num+? where gid=?";
        template.update(sql, num, gid);
    }

    /**
     *
     * @param goods
     */
    @Override
    public void createGoods(Goods goods) {
        //货物入库
        String sql = "insert into goods(gid,name,type,price,picture,num,description,location,rentorbuy) values(?,?,?,?,?,?,?,?,?)";
        template.update(sql,
                goods.getGid(),
                //UuidUtil.getUuid(),
                goods.getName(),
                goods.getType(),
                goods.getPrice(),
                goods.getPicture(),
                goods.getNum(),
                goods.getDescription(),
                goods.getLocation(),
                goods.getRentorbuy());
    }

    /**
     *
     * @param gid
     */
    @Override
    public void deleteGoods(String gid) {
        String sql = "update goods set num=0 where gid=?";
        template.update(sql,gid);
    }

    /**
     *
     * @param goods
     */
    @Override
    public void updateGoods(Goods goods) {
        String sql = "update goods set name=?, type=?, price=?, picture=?, num=?, description=?, location=?, rentorbuy=? where gid=?";
        template.update(sql,
                goods.getName(),
                goods.getType(),
                goods.getPrice(),
                goods.getPicture(),
                goods.getNum(),
                goods.getDescription(),
                goods.getLocation(),
                goods.getRentorbuy(),
                goods.getGid());
    }

    /**
     *
     * @param type
     * @return
     */
    @Override
    public List<Goods> searchByType(String type) {
        String sql = "select * from goods where type = ?";
        return template.query(sql, new BeanPropertyRowMapper<Goods>(Goods.class), type);
    }

    /**
     *
     * @param start
     * @param pageSize
     * @param keyword
     * @return
     */
    @Override
    public List<Goods> searchByKeyword(int start, int pageSize, String keyword) {
        String sql = "SELECT * FROM goods WHERE NAME LIKE ? OR `type` LIKE ? limit ? , ?";
        System.out.println(start);
        return template.query(sql, new BeanPropertyRowMapper<Goods>(Goods.class), "%" + keyword + "%", "%" + keyword + "%", start, pageSize);
    }

    /**
     *
     * @param keyword
     * @return
     */
    @Override
    public List<Goods> searchByKeyword(String keyword) {
        String sql = "SELECT * FROM goods WHERE NAME LIKE ? OR `type` LIKE ?";
        return template.query(sql, new BeanPropertyRowMapper<Goods>(Goods.class), "%" + keyword + "%", "%" + keyword + "%");
    }

    /**
     *
     * @param keyword
     * @return
     */
    @Override
    public int searchByKeywordNum(String keyword) {
        String sql = "SELECT count(*) FROM goods WHERE NAME LIKE ? OR `type` LIKE ?";
        return template.queryForObject(sql, Integer.class, "%" + keyword + "%", "%" + keyword + "%");
    }

    /**
     *
     * @param gid
     * @return
     */
    @Override
    public double searchPrice(String gid) {
        String sql = "select price from goods where gid = ?";
        return template.queryForObject(sql, Double.class, gid);
    }
}
