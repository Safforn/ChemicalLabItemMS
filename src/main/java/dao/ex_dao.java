package dao;

import domain.Ex_Warehouse;

import java.util.List;

public interface ex_dao {
    /**
     * 添加出库单
     * @param ex_warehouse
     * @return
     */
    boolean add(Ex_Warehouse ex_warehouse);

    /**
     * 查询所有出库单
     * @return
     */
    List<Ex_Warehouse> search();
    List<Ex_Warehouse> getMaxId();
}
