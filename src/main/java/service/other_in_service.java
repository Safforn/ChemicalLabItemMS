package service;

import domain.Other_in_Warehouse;
import domain.template_order;

import java.util.List;

public interface other_in_service {
    /**
     * 查询所有其他入库单
     * @return
     */
    List<Other_in_Warehouse> findAll();

    /**
     * 新建入库单
     * @param temp
     * @return
     */
    boolean add(template_order temp);
}
