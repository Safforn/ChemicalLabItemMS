package service.service_temp.impl;

import dao.dao_temp.AddtoWarehouseDao;
import dao.dao_temp.ExwarehouseDao;
import dao.dao_temp.GoodsDao;
import dao.dao_temp.OrderDao;
import dao.dao_temp.impl.AddtoWarehouseDaoImpl;
import dao.dao_temp.impl.ExwarehouseDaoImpl;
import dao.dao_temp.impl.GoodsDaoImpl;
import dao.dao_temp.impl.OrderDaoImpl;
import domain.AddToWarehouse;
import domain.ExWarehouse;
import domain.Goods;
import domain.Order;
import service.service_temp.AdminService;
import util.UuidUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdminServiceImpl implements AdminService {
    OrderDao orderDao = new OrderDaoImpl();
    GoodsDao goodsDao = new GoodsDaoImpl();
    ExwarehouseDao exwarehouseDao = new ExwarehouseDaoImpl();
    AddtoWarehouseDao addtoWarehouseDao = new AddtoWarehouseDaoImpl();


    /**
     *
     * @return
     */
    @Override
    public List<Goods> searchGoods() {
        return goodsDao.searchAll();
    }

    /**
     *
     * @param order
     * @param uid
     * @return
     */
    @Override
    public boolean exWarehouse(Order order, String uid) {
        ExWarehouse exWarehouse = new ExWarehouse(UuidUtil.getUuid(), order.getOid(), uid);
        exwarehouseDao.exhausting(exWarehouse);
        return true;
    }

    /**
     *
     * @param order
     * @param uid
     * @return
     */
    @Override
    public boolean addToWarehouseByOrder(Order order, String uid) {
        AddToWarehouse addToWarehouse = new AddToWarehouse(UuidUtil.getUuid(), uid, order.getGid(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), order.getNum());
        addtoWarehouseDao.recving(addToWarehouse);
        Goods goods = goodsDao.searchByGid(order.getGid());
        if(goods==null){
            goodsDao.createGoods(goods);
        } else {
            goodsDao.updateGoodsNumber(goods.getGid(),order.getNum());
        }
        return true;
    }

    /**
     *
     * @param order
     * @return
     */
    @Override
    public boolean cancelOrder(Order order) {
        goodsDao.updateGoodsNumber(order.getGid(), order.getNum());
        return true;
    }

    /**
     *
     * @param type
     * @return
     */
    @Override
    public List<Order> searchOrderByType(int type) {
        return orderDao.searchByType(type);
    }

}
