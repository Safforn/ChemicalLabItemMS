package service.impl;

import dao.get_or_borrow_dao;
import dao.impl.get_or_borrow_dao_impl;
import dao.impl.object_entry_dao_impl;
import dao.impl.reminder_dao_impl;
import dao.object_entry_dao;
import dao.reminder_dao;
import domain.Object_Entry;
import domain.Reminder;
import domain.get_or_borrow_Requisition;
import domain.template_order;
import service.get_or_borrow_service;
import util.UuidUtil;

import java.util.Date;
import java.util.List;
import static util.UuidUtil.getCurrentTime;

public class get_or_borrow_service_impl implements get_or_borrow_service {
    private get_or_borrow_dao getOrBorrowDao = new get_or_borrow_dao_impl();
    private object_entry_dao objectEntryDao = new object_entry_dao_impl();
    private reminder_dao reminderDao = new reminder_dao_impl();

    @Override
    public boolean createOrUpdate(template_order tando) {
        get_or_borrow_Requisition table = (get_or_borrow_Requisition) tando.getTable();
        List<Object_Entry> order = tando.getOrder();
        System.out.println("-- service : " + table.getGet_or_borrow_requisition_id());
        if (table.getGet_or_borrow_requisition_id() == null) {
            return createTable(table, order);
        }
        else {
            return changeTable(table, order);
        }
    }

    private boolean createTable(get_or_borrow_Requisition table, List<Object_Entry> order) {
        /**
         * 新增物品列表
         * 系统填写：申请单id，物品单id，提交时间
         */
        table.setRequisition_date(getCurrentTime());
        table.setGet_or_borrow_requisition_id(UuidUtil.getUuid());
//        String orderId = UuidUtil.getUuid();
//        table.setGet_or_borrow_order_id(orderId);
        for (Object_Entry object_entry : order) {
            object_entry.setObject_entry_id(UuidUtil.getUuid());
        }
        return objectEntryDao.addEntry(order, table.getGet_or_borrow_order_id()) && getOrBorrowDao.createTable(table);
    }

    private boolean changeTable(get_or_borrow_Requisition table, List<Object_Entry> order) {
        if (!getOrBorrowDao.updateTable(table)) {
            return false;
        }
        if (!objectEntryDao.deleteEntryByOrder(table.getGet_or_borrow_order_id())) {
            return false;
        }
        for (Object_Entry object_entry : order) {
            object_entry.setObject_entry_id(UuidUtil.getUuid());
        }
        if (objectEntryDao.addEntry(order, table.getGet_or_borrow_order_id())) {

            if (table.getType() == 1) {
                //填写催还单
                return reminderDao.add(new Reminder(UuidUtil.getUuid(), table.getGet_or_borrow_requisition_id()));
            }
        }
        return false;
    }

    @Override
    public boolean approvalTable(get_or_borrow_Requisition table) {
        table.setApproval_date(getCurrentTime());
        return getOrBorrowDao.updateTable(table);
    }

    @Override
    public boolean deleteTable(String tableId) {
        get_or_borrow_Requisition get_or_borrow_requisition = getOrBorrowDao.searchTableById(tableId);
        return objectEntryDao.deleteEntryByOrder(get_or_borrow_requisition.getGet_or_borrow_order_id()) && getOrBorrowDao.deleteTable(tableId);
    }

//    @Override
//    public void submitTable(String tableId) {
//
//    }

    @Override
    public template_order searchTableById(String tableId) {
        get_or_borrow_Requisition table = getOrBorrowDao.searchTableById(tableId);
        List<Object_Entry> order = objectEntryDao.search(table.getGet_or_borrow_order_id());
        return new template_order(table, order);
    }

    @Override
    public List<get_or_borrow_Requisition> searchTableByUser(String userId) {
        return getOrBorrowDao.searchTableByUser(userId);
    }

    @Override
    public List<get_or_borrow_Requisition> searchTableByState(int state) {
        return getOrBorrowDao.searchTableByState(state);
    }

    @Override
    public List<get_or_borrow_Requisition> searchBorrowUnreturn() {
        return getOrBorrowDao.searchBorrowUnreturn();
    }
}
