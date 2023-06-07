package service;

import domain.Borrow_in_Warehouse;

import java.util.List;

public interface borrow_in_service {
    /**
     * 添加借用入库表单
     * @param table
     * @return
     */
    boolean add(Borrow_in_Warehouse table);

}
