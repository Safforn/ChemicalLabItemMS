package service;

import domain.Purchase_in_Warehouse;
import domain.template_order;

public interface purchase_in_service {
    /**
     * 新增购买入库单
     * @param temp
     */
    void add(template_order temp);
}
