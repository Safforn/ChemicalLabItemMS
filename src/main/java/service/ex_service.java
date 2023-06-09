package service;

import domain.Ex_Warehouse;
import domain.Object_Entry;

import java.util.List;

public interface ex_service {
    /**
     * 添加出库单
     * @param ex_warehouse
     * @return
     */
    boolean add(Ex_Warehouse ex_warehouse, List<Object_Entry> order);

    /**
     * 查询所有出库单
     * @return
     */
    List<Ex_Warehouse> search();
}
