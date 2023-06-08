package service.impl;

import dao.impl.item_dao_impl;
import dao.impl.object_entry_dao_impl;
import dao.impl.purchase_in_dao_impl;
import dao.impl.purchase_requisition_dao_impl;
import dao.item_dao;
import dao.object_entry_dao;
import dao.purchase_in_dao;
import dao.purchase_requisition_dao;
import domain.Object_Entry;
import domain.Purchase_in_Warehouse;
import domain.template_order;
import service.purchase_in_service;
import util.UuidUtil;

import java.util.List;

public class purchase_in_service_impl implements purchase_in_service {
    private object_entry_dao objectEntryDao = new object_entry_dao_impl();
    private purchase_in_dao purchaseInDao = new purchase_in_dao_impl();
    private purchase_requisition_dao purchaseRequisitionDao = new purchase_requisition_dao_impl();
    private item_dao itemDao = new item_dao_impl();
    @Override
    public boolean add(template_order temp) {
        Purchase_in_Warehouse table = (Purchase_in_Warehouse)temp.getTable();//采购入库表
        List<Object_Entry> order = temp.getOrder();  // 入库清单
        List<String> inOrderId = purchaseInDao.getInOrder(table.getPurchase_order_id());  // 从DS里获取的 与采购清单关联的入库清单
        String InId = UuidUtil.getUuid();
        table.setPurchase_in_warehouse_id(InId);  //为入库表创建id

        if (inOrderId.size() == 0) {  // 首次入库
            String id = UuidUtil.getUuid();   //生成入库清单的id
            table.setPurchase_in_order_id(id);  // 写入采购入库表
            for (Object_Entry object_entry : order) {
                object_entry.setObject_entry_id(UuidUtil.getUuid());
            }
            check(table);
            return objectEntryDao.addEntry(order, id) && purchaseInDao.add(table);
        }
        else {
            String id = inOrderId.get(0);
            for (Object_Entry object_entry : order) {
                if (objectEntryDao.findOne(id, object_entry.getObject_id()) > 0) {  // 去入库清单里查找当前物品
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
            check(table);
            return purchaseInDao.add(table);
        }
    }

    private void check(Purchase_in_Warehouse InId) {
        List<Object_Entry> purchaseList = objectEntryDao.search(InId.getPurchase_order_id());
        List<Object_Entry> inList = objectEntryDao.search(InId.getPurchase_in_order_id());
        boolean flag = true;
        for (Object_Entry p : purchaseList) {
            for (Object_Entry i : inList) {
                if (p.getObject_id().equals(i.getObject_id())) {
                    if (p.getNum() != i.getNum()) {
                        flag = false;
                        break;
                    }
                }
            }
        }
        if (flag) {
            purchaseRequisitionDao.changeState(InId.getPurchase_order_id(), 0);
        }
    }

}
