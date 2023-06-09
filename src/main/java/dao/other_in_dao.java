package dao;

import domain.Other_in_Warehouse;
import domain.Purchase_in_Warehouse;

import java.util.List;

public interface other_in_dao {

    /**
     * 新建表单
     * @param table
     */
    boolean add(Other_in_Warehouse table);

    /**
     * 查询入库单
     * @return
     */
    List<Other_in_Warehouse> search();
    List<Other_in_Warehouse> getMaxId();
}
