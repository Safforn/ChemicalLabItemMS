package service;

import domain.Item;
import domain.Object_Entry;

import java.util.List;

public interface item_service {
    /**
     * 获取订单
     * @param object_id
     * @return
     */
    Item search(String object_id);

    /**
     * 添加物品
     * @param items
     * @return
     */
    boolean add(List<Item> items);
}
