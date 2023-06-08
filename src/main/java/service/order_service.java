package service;

import domain.Object_Entry;

import java.util.List;

public interface order_service {
    /**
     * 获取订单
     * @param orderId
     * @return
     */
    List<Object_Entry> search(String orderId);

    /**
     * 删除订单物品列表
     * @param orderId
     * @return
     */
    boolean deleteEntryByOrder(String orderId);
}
