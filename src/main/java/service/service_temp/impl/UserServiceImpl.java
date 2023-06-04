package service.service_temp.impl;

import dao.dao_temp.ExwarehouseDao;
import dao.dao_temp.GoodsDao;
import dao.dao_temp.OrderDao;
import dao.dao_temp.UserDao;
import dao.dao_temp.impl.ExwarehouseDaoImpl;
import dao.dao_temp.impl.GoodsDaoImpl;
import dao.dao_temp.impl.OrderDaoImpl;
import dao.dao_temp.impl.UserDaoImpl;
import domain.ExWarehouse;
import domain.Goods;
import domain.Order;
import domain.User;
import service.service_temp.UserService;
import util.UuidUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    private OrderDao orderDao = new OrderDaoImpl();
    private GoodsDao goodsDao = new GoodsDaoImpl();
    private ExwarehouseDao exwarehouseDao = new ExwarehouseDaoImpl();
    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
        //1.根据用户名查询用户对象
        User u = userDao.findByUid(user.getUid());
        //判断 u 是否为 null
        if(u != null){
            //用户名存在，注册失败
            return false;
        }
        //2.保存用户信息
        userDao.save(user);
        return true;
    }

    /**
     *
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return userDao.findByUidAndPassword(user.getUid(),user.getPassword());
    }



    /**
     *
     * @param uid
     * @return
     */
    @Override
    public List<Goods> searchGoods(String uid) {
        return goodsDao.searchAll();
    }

    /**
     *
     * @param order
     * @return
     */
    @Override
    public String leaseAndBuy(Order order) {
        order.setOid(UuidUtil.getUuid());
        Goods good = goodsDao.searchByGid(order.getGid());
        if (good.getNum().intValue() < order.getNum().intValue()) {
            return "仓库数量不足！";
        }
        goodsDao.updateGoodsNumber(order.getGid(), -order.getNum());
        if (order.getStarttime() == null || order.getStarttime() == "") {
            order.setStarttime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }
        if (!orderDao.insertOrder(order)) {
            return "订单创建失败！";
        }
//        eid,oid,uid
        ExWarehouse exWarehouse = new ExWarehouse();
        exWarehouse.setEid(UuidUtil.getUuid());
        exWarehouse.setOid(order.getOid());
        exWarehouse.setUid(order.getUid());
        exwarehouseDao.exhausting(exWarehouse);
        return "成功！";
    }

    /**
     *
     * @param type
     * @param uid
     * @return
     */
    @Override
    public List<Order> searchOrderByType(int type, String uid) {
        return orderDao.searchByTypeAndUser(type, uid);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public User loginByUid(String uid) {
        return userDao.findByUid(uid);
    }

}

