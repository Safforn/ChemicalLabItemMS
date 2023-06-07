package dao;

import domain.Purchase_in_Warehouse;

import java.util.List;

public interface purchase_in_dao {
    /**
     * 新建表单
     * @param table
     */
    boolean add(Purchase_in_Warehouse table);

    /**
     * 查询采购订单是否存在
     * @param orderId
     * @return
     */
    List<String> getInOrder(String orderId);
}
