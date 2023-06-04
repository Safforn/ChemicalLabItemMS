package Dao;

import dao.dao_temp.GoodsDao;
import dao.dao_temp.impl.GoodsDaoImpl;
import domain.Goods;
import org.junit.Test;


public class GoodsDaoTest {
    GoodsDao goodsDao = new GoodsDaoImpl();

    @Test
    public void searchAll() {
        goodsDao.searchAll();
    }

    @Test
    public void searchByGid() {
        goodsDao.searchByGid("7c85a0dadf9e4cab8d8daccbc2acc1ac");
    }

    @Test
    public void updateGoodsNumber() {
        goodsDao.updateGoodsNumber("",0);
    }

    @Test
    public void createGoods() {
        //Goods goods = new Goods("","","",0.0,"",0,"","",0);
        Goods goods = new Goods("1122","","",0.0,"",10,"","",0);
        goodsDao.createGoods(goods);
    }

    @Test
    public void deleteGoods() {
        goodsDao.deleteGoods("");
    }

    @Test
    public void updateGoods() {
        Goods goods = new Goods("","","",0.0,"",0,"","",0);
        goodsDao.updateGoods(goods);
    }
}