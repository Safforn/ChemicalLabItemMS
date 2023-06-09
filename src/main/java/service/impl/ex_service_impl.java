package service.impl;

import dao.ex_dao;
import dao.impl.ex_dao_impl;
import dao.impl.item_dao_impl;
import dao.impl.object_entry_dao_impl;
import dao.item_dao;
import dao.object_entry_dao;
import domain.Ex_Warehouse;
import domain.Object_Entry;
import service.ex_service;
import util.UuidUtil;

import java.util.List;

public class ex_service_impl implements ex_service {
    private ex_dao exDao = new ex_dao_impl();
    private object_entry_dao objectEntryDao = new object_entry_dao_impl();
    private item_dao itemDao = new item_dao_impl();
    @Override
    public boolean add(Ex_Warehouse ex_warehouse, List<Object_Entry> order) {
        ex_warehouse.setEx_warehouse_id(UuidUtil.getEW());
        ex_warehouse.setDate(UuidUtil.getCurrentTime());
        if (!exDao.add(ex_warehouse)) {
            return false;
        }

        objectEntryDao.addEntry(order, ex_warehouse.getEx_order_id());

        // 修改库存
//        String orderId = ex_warehouse.getEx_order_id();
//        List<Object_Entry> objectEntries = objectEntryDao.search(orderId);
        for (Object_Entry object_entry : order) {
            if (!itemDao.changeNum(object_entry.getObject_id(), -1 * object_entry.getNum())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Ex_Warehouse> search() {
        return exDao.search();
    }
}
