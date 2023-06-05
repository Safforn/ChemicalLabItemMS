package service.impl;

import dao.get_or_borrow_dao;
import dao.impl.get_or_borrow_dao_impl;
import domain.get_or_borrow_Requisition;
import org.joda.time.DateTime;
import service.get_or_borrow_service;
import util.UuidUtil;

import java.text.SimpleDateFormat;
import java.util.List;

public class get_or_borrow_service_impl implements get_or_borrow_service {
    private get_or_borrow_dao getOrBorrowDao = new get_or_borrow_dao_impl();
    private SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String time = sfd.format(new java.util.Date());

    @Override
    public void createOrUpdate(get_or_borrow_Requisition table) {
        if (table.getGet_or_borrow_requisition_id().length() == 0) {
            changeTable(table);
        }
        else {
            changeTable(table);
        }
    }

    @Override
    public void createTable(get_or_borrow_Requisition table) {
        /**
         * 新增物品列表
         * 系统填写：申请单id，物品单id，提交时间
         */
        table.setApproval_date(DateTime.parse(time));
        table.setGet_or_borrow_requisition_id(UuidUtil.getUuid());
        String orderId = UuidUtil.getUuid();
        table.setGet_or_borrow_order_id(orderId);
        getOrBorrowDao.createTable(table);
    }

    @Override
    public void changeTable(get_or_borrow_Requisition table) {
        getOrBorrowDao.updateTableByApplicant(table);
    }

    @Override
    public void approvalTable(get_or_borrow_Requisition table) {
        table.setApproval_date(DateTime.parse(time));
        getOrBorrowDao.updateTableByApproval(table);
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
