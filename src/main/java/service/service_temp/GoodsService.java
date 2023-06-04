package service.service_temp;

import domain.Goods;
import domain.bean.PageBean;

import java.util.List;

public interface GoodsService {
    void addToWarehouse(Goods goods, String uid);
    boolean exWarehouse(String gid ,int num, String uid, String oid);
    void updateGoods(Goods goods);
    void deleteGoods(String gid);
    Goods findByGid(String gid);
    List<Goods> findAll();
    List<Goods> findByType(String type);
    List<Goods> searchByKeyword(String keyword);
    PageBean<Goods> pageQuery(int currentPage, int pageSize, String gname);
    double searchPrice(String gid);
}
