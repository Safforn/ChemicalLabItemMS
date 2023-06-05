package service.impl;

import dao.get_or_borrow_dao;
import dao.impl.get_or_borrow_dao_impl;
import domain.get_or_borrow_Requisition;
import service.get_or_borrow_service;

import java.text.SimpleDateFormat;
import java.util.List;

public class get_or_borrow_service_impl implements get_or_borrow_service {
    private get_or_borrow_dao getOrBorrowDao = new get_or_borrow_dao_impl();
    private SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String time = sfd.format(new java.util.Date());

    @Override
    public void createTable(get_or_borrow_Requisition table) {

    }

    @Override
    public void updateTable(get_or_borrow_Requisition table) {

    }

    @Override
    public void deleteTable(String tableId) {

    }

    @Override
    public void submitTable(String tableId) {

    }

    @Override
    public get_or_borrow_Requisition searchTableById(String tableId) {
        return null;
    }

    @Override
    public List<get_or_borrow_Requisition> searchTableByUser(String userId) {
        return null;
    }

    @Override
    public List<get_or_borrow_Requisition> searchTableByState(int state) {
        return null;
    }
}
