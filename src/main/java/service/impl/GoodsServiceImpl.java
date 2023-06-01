package service.impl;

import dao.AddtoWarehouseDao;
import dao.ExwarehouseDao;
import dao.GoodsDao;
import dao.impl.AddtoWarehouseDaoImpl;
import dao.impl.ExwarehouseDaoImpl;
import dao.impl.GoodsDaoImpl;
import domain.AddToWarehouse;
import domain.ExWarehouse;
import domain.Goods;
import domain.bean.PageBean;
import service.GoodsService;
import util.UuidUtil;

import java.text.SimpleDateFormat;
import java.util.List;

public class GoodsServiceImpl implements GoodsService {
    private GoodsDao goodsDao = new GoodsDaoImpl();
    private AddtoWarehouseDao addtoWarehouseDao = new AddtoWarehouseDaoImpl();
    private ExwarehouseDao exwarehouseDao = new ExwarehouseDaoImpl();
    private SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String time = sfd.format(new java.util.Date());

    /**
     *
     * @param goods
     * @param uid
     */
    @Override
    public void addToWarehouse(Goods goods, String uid) {
        Goods goods1 = goodsDao.checkExistByGid(goods.getGid());  //新增货物时检查是否存在过(数量可能为0)
        if(goods1 == null){
            goodsDao.createGoods(goods);
        } else {
            goodsDao.updateGoodsNumber(goods.getGid(), goods.getNum());
        }
        //入库表的更新
        AddToWarehouse addToWarehouse = new AddToWarehouse();
        addToWarehouse.setAid(UuidUtil.getUuid());
        addToWarehouse.setUid(uid);
        addToWarehouse.setGid(goods.getGid());
        addToWarehouse.setTime(time);
        addToWarehouse.setNum(goods.getNum());
        addtoWarehouseDao.recving(addToWarehouse);
    }

    /**
     *
     * @param gid
     * @param num
     * @param uid
     * @param oid
     * @return
     */
    @Override
    public boolean exWarehouse(String gid, int num, String uid, String oid) {
        Goods goods = goodsDao.searchByGid(gid);
        if(goods.getNum()<num){
            return false;
        } else {
            goods.setNum(goods.getNum()-num);
            goodsDao.updateGoods(goods);
            //出库表的更新
            ExWarehouse exWarehouse = new ExWarehouse();
            exWarehouse.setUid(uid);
            exWarehouse.setOid(oid);
            exWarehouse.setEid(UuidUtil.getUuid());
            exwarehouseDao.exhausting(exWarehouse);
            return true;
        }
    }

    /**
     *
     * @param goods
     */
    @Override
    public void updateGoods(Goods goods) {
        goodsDao.updateGoods(goods);
    }

    /**
     *
     * @param gid
     */
    @Override
    public void deleteGoods(String gid) {
        goodsDao.deleteGoods(gid);
    }

    /**
     *
     * @param gid
     * @return
     */
    @Override
    public Goods findByGid(String gid) {
        return goodsDao.searchByGid(gid);
    }

    /**
     *
     * @return
     */
    @Override
    public List<Goods> findAll() {
        return goodsDao.searchAll();
    }

    /**
     *
     * @param type
     * @return
     */
    @Override
    public List<Goods> findByType(String type) {
        return goodsDao.searchByType(type);
    }

    /**
     *
     * @param keyword
     * @return
     */
    @Override
    public List<Goods> searchByKeyword(String keyword) {
        return goodsDao.searchByKeyword(keyword);
    }

    /**
     *
     * @param currentPage
     * @param pageSize
     * @param gname
     * @return
     */
    @Override
    public PageBean<Goods> pageQuery(int currentPage, int pageSize, String gname) {
        PageBean<Goods> pb = new PageBean<Goods>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        int totalCount = goodsDao.searchByKeywordNum(gname);
        pb.setTotalCount(totalCount);
        int start = (currentPage - 1) * pageSize;
        List<Goods> list = goodsDao.searchByKeyword(start,pageSize,gname);
        pb.setList(list);
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize :(totalCount / pageSize) + 1 ;
        pb.setTotalPage(totalPage);
        return pb;
    }

    /**
     *
     * @param gid
     * @return
     */
    @Override
    public double searchPrice(String gid) {
        return goodsDao.searchPrice(gid);
    }
}