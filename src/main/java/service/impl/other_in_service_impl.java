package service.impl;

import dao.impl.object_entry_dao_impl;
import dao.impl.other_in_dao_impl;
import dao.object_entry_dao;
import dao.other_in_dao;
import domain.Object_Entry;
import domain.Other_in_Warehouse;
import domain.template_order;
import service.other_in_service;
import util.UuidUtil;

import java.util.List;
import java.util.UUID;

public class other_in_service_impl implements other_in_service {
    private other_in_dao otherInDao = new other_in_dao_impl();
    private object_entry_dao objectEntryDao = new object_entry_dao_impl();
    @Override
    public List<Other_in_Warehouse> findAll() {
        return otherInDao.search();
    }

    @Override
    public boolean add(template_order temp) {
        Other_in_Warehouse other_in_warehouse = (Other_in_Warehouse) temp.getTable();
        List<Object_Entry> order = temp.getOrder();

        other_in_warehouse.setOther_in_warehouse_id(UuidUtil.getUuid());
        other_in_warehouse.setDate(UuidUtil.getCurrentTime());

        for (Object_Entry object_entry : order) {
            if (objectEntryDao.findOne(other_in_warehouse.getOther_in_order_id(), object_entry.getObject_id()) > 0) {  // 去入库清单里查找当前物品
                if (!objectEntryDao.updateOne(other_in_warehouse.getOther_in_order_id(), object_entry.getObject_id(), object_entry.getNum())) {
                    return false;
                }
            }
            else {
                if (object_entry.getObject_id() == null) object_entry.setObject_id(UuidUtil.getUuid());
                object_entry.setObject_entry_id(UuidUtil.getUuid());
                if (!objectEntryDao.addOne(object_entry, other_in_warehouse.getOther_in_order_id())) {
                    return false;
                }
            }
        }
        return otherInDao.add(other_in_warehouse);
    }
}
