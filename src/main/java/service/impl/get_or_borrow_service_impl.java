package service.impl;

import dao.get_or_borrow_dao;
import dao.impl.get_or_borrow_dao_impl;
import dao.impl.object_entry_dao_impl;
import dao.object_entry_dao;
import domain.Object_Entry;
import domain.get_or_borrow_Requisition;
import domain.template_order;
import org.joda.time.DateTime;
import service.get_or_borrow_service;
import util.UuidUtil;

import java.text.SimpleDateFormat;
import java.util.List;

public class get_or_borrow_service_impl implements get_or_borrow_service {
    private get_or_borrow_dao getOrBorrowDao = new get_or_borrow_dao_impl();
    private object_entry_dao objectEntryDao = new object_entry_dao_impl();
    private SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String time = sfd.format(new java.util.Date());

    @Override
    public void createOrUpdate(template_order tando) {
        get_or_borrow_Requisition table = (get_or_borrow_Requisition) tando.getTable();
        List<Object_Entry> order = tando.getOrder();
        if (table.getGet_or_borrow_requisition_id().length() == 0) {
            createTable(table, order);
        }
        else {
            changeTable(table, order);
        }
    }

    @Override
    public void createTable(get_or_borrow_Requisition table, List<Object_Entry> order) {
        /**
         * 新增物品列表
         * 系统填写：申请单id，物品单id，提交时间
         */
        table.setApproval_date(DateTime.parse(time));
        table.setGet_or_borrow_requisition_id(UuidUtil.getUuid());
        String orderId = UuidUtil.getUuid();
        table.setGet_or_borrow_order_id(orderId);
        getOrBorrowDao.createTable(table);
        objectEntryDao.addEntry(order, orderId);
    }

    @Override
    public void changeTable(get_or_borrow_Requisition table, List<Object_Entry> order) {
        getOrBorrowDao.updateTable(table);
        objectEntryDao.deleteEntryByOrder(table.getGet_or_borrow_order_id());
        objectEntryDao.addEntry(order, table.getGet_or_borrow_order_id());
    }

    @Override
    public void approvalTable(get_or_borrow_Requisition table) {
        table.setApproval_date(DateTime.parse(time));
        getOrBorrowDao.updateTable(table);
    }

    @Override
    public void deleteTable(String tableId) {
        getOrBorrowDao.deleteTable(tableId);
    }

//    @Override
//    public void submitTable(String tableId) {
//
//    }

    @Override
    public get_or_borrow_Requisition searchTableById(String tableId) {
        return getOrBorrowDao.searchTableById(tableId);
    }

    @Override
    public List<get_or_borrow_Requisition> searchTableByUser(String userId) {
        return getOrBorrowDao.searchTableByUser(userId);
    }

    @Override
    public List<get_or_borrow_Requisition> searchTableByState(int state) {
        return getOrBorrowDao.searchTableByState(state);
    }
}
