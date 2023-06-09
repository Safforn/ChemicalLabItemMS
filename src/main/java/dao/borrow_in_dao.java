package dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import domain.Borrow_in_Warehouse;

import java.util.List;

public interface borrow_in_dao {
    /**
     * 添加借用归还单
     * @param table
     * @return
     */
    boolean add(Borrow_in_Warehouse table);
    List<Borrow_in_Warehouse> getMaxId();

}
