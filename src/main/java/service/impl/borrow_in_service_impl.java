package service.impl;

import dao.borrow_in_dao;
import dao.impl.borrow_in_dao_impl;
import domain.Borrow_in_Warehouse;
import service.borrow_in_service;
import util.UuidUtil;

public class borrow_in_service_impl implements borrow_in_service {
    private borrow_in_dao borrowInDao = new borrow_in_dao_impl();
    @Override
    public void add(Borrow_in_Warehouse table) {
        table.setBorrow_in_warehouse_id(UuidUtil.getUuid());
        borrowInDao.add(table);
    }
}
