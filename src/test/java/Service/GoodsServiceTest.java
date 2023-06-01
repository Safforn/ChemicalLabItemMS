package Service;

import domain.Goods;
import org.junit.Test;
import service.GoodsService;
import service.impl.GoodsServiceImpl;

import static org.junit.Assert.*;

public class GoodsServiceTest {
    GoodsService goodsService = new GoodsServiceImpl();

    @Test
    public void addToWarehouse() {
        Goods goods = new Goods("123fee1acf7448b985e80e228631fb39","","",0.0,"",10,"","",0);
        goodsService.addToWarehouse(goods,"zhangsan");
    }

    @Test
    public void exWarehouse() {
        goodsService.exWarehouse("123fee1acf7448b985e80e228631fb39",1,"zhangsan","1");
    }

    @Test
    public void updateGoods() {
        Goods goods = new Goods("1122","","",0.0,"",5,"","",0);
        goodsService.updateGoods(goods);
    }

    @Test
    public void deleteGoods() {
        goodsService.deleteGoods("1122");
    }
}