package dao;

import domain.Item;

import java.util.List;

public interface item_dao {
    /**
     * 添加物品
     * @param item
     * @return
     */
    boolean add(Item item);

    /**
     * 改变库存数量
     * @param id
     * @param num
     * @return
     */
    boolean changeNum(String id, int num);

    /**
     * 查询所有库存
     * @return
     */
    List<Item> search();

    /**
     * 根据id查询一样物品
     * @param object_id
     * @return
     */
    Item findByObjectId(String object_id);
}
