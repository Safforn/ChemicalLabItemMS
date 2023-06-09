package service.impl;

import dao.borrow_in_dao;
import dao.get_or_borrow_dao;
import dao.impl.borrow_in_dao_impl;
import dao.impl.get_or_borrow_dao_impl;
import dao.impl.item_dao_impl;
import dao.impl.object_entry_dao_impl;
import dao.item_dao;
import dao.object_entry_dao;
import domain.Borrow_in_Warehouse;
import domain.Object_Entry;
import domain.get_or_borrow_Requisition;
import service.borrow_in_service;
import util.UuidUtil;

import java.util.List;

public class borrow_in_service_impl implements borrow_in_service {
    private borrow_in_dao borrowInDao = new borrow_in_dao_impl();
    private get_or_borrow_dao getOrBorrowDao = new get_or_borrow_dao_impl();
    private object_entry_dao objectEntryDao = new object_entry_dao_impl();
    private item_dao itemDao = new item_dao_impl();
    @Override
    public boolean add(Borrow_in_Warehouse table) {
        table.setBorrow_in_warehouse_id(UuidUtil.getBW());
        get_or_borrow_Requisition getOrBorrowRequisition = getOrBorrowDao.searchTableByOrderId(table.getBorrow_in_order_id());
        getOrBorrowRequisition.setState(4);
        if (!getOrBorrowDao.updateTable(getOrBorrowRequisition)) {
            return false;
        }
        if (!borrowInDao.add(table)) {
            return false;
        }
        // 修改库存
        String orderId = table.getBorrow_in_order_id();
        List<Object_Entry> objectEntries = objectEntryDao.search(orderId);
        for (Object_Entry object_entry : objectEntries) {
            if (!itemDao.changeNum(object_entry.getObject_id(), object_entry.getNum())) {
                return false;
            }
        }
        return true;
    }

}
