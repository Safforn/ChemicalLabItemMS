package dao.dao_temp;

import domain.Goods;

import java.util.List;

public interface GoodsDao {
    List<Goods> searchAll();
    Goods searchByGid(String gid);
    Goods checkExistByGid(String gid);
    void updateGoodsNumber(String gid, int increase);
    void createGoods(Goods goods);
    void deleteGoods(String gid);
    void updateGoods(Goods goods);
    List<Goods> searchByType(String type);
    List<Goods> searchByKeyword(String keyword);
    List<Goods> searchByKeyword(int start,int pageSize,String keyword);
    int searchByKeywordNum(String keyword);
    double searchPrice(String gid);
}
