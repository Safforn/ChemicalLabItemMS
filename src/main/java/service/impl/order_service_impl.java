package service.impl;

import dao.impl.object_entry_dao_impl;
import dao.object_entry_dao;
import domain.Object_Entry;
import service.order_service;

import java.util.List;

public class order_service_impl implements order_service {
    private object_entry_dao objectEntryDao = new object_entry_dao_impl();
    @Override
    public List<Object_Entry> search(String orderId) {
        return objectEntryDao.search(orderId);
    }
}
