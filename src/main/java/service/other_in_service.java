package service;

import domain.Other_in_Warehouse;

import java.util.List;

public interface other_in_service {
    /**
     * 查询所有其他入库单
     * @return
     */
    List<Other_in_Warehouse> findAll();

    /**
     * 新建入库单
     * @param other_in_warehouse
     * @return
     */
    boolean add(Other_in_Warehouse other_in_warehouse);
}
