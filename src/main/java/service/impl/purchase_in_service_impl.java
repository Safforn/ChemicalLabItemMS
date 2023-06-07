package service.impl;

import dao.impl.object_entry_dao_impl;
import dao.impl.purchase_in_dao_impl;
import dao.object_entry_dao;
import dao.purchase_in_dao;
import domain.Object_Entry;
import domain.Purchase_in_Warehouse;
import domain.template_order;
import service.purchase_in_service;
import util.UuidUtil;

import java.util.List;

public class purchase_in_service_impl implements purchase_in_service {
    private object_entry_dao objectEntryDao = new object_entry_dao_impl();
    private purchase_in_dao purchaseInDao = new purchase_in_dao_impl();

    @Override
    public boolean add(template_order temp) {
        Purchase_in_Warehouse table = (Purchase_in_Warehouse)temp.getTable();//采购入库表
        List<Object_Entry> order = temp.getOrder();  // 入库清单
        List<String> inOrderId = purchaseInDao.getInOrder(table.getPurchase_order_id());  // 从DS里获取的 与采购清单关联的入库清单

        table.setPurchase_in_warehouse_id(UuidUtil.getUuid());  //为入库表创建id

        if (inOrderId.size() == 0) {  // 首次入库
            String id = UuidUtil.getUuid();   //生成入库清单的id
            table.setPurchase_in_order_id(id);  // 写入采购入库表
            for (Object_Entry object_entry : order) {
                object_entry.setObject_entry_id(UuidUtil.getUuid());
            }
            return objectEntryDao.addEntry(order, id) && purchaseInDao.add(table);
        }
        else {
            String id = inOrderId.get(0);
            for (Object_Entry object_entry : order) {
                if (objectEntryDao.findOne(id, object_entry.getObject_id()) > 0) {
                    if (!objectEntryDao.updateOne(id, object_entry.getObject_id(), object_entry.getNum())) {
                        return false;
                    }
                }
                else {
                    if (!objectEntryDao.addOne(object_entry, id)) {
                        return false;
                    }
                }
            }

            table.setPurchase_in_order_id(id);
            return purchaseInDao.add(table);
        }
    }
}
