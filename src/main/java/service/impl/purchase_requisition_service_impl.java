package service.impl;

import dao.impl.object_entry_dao_impl;
import dao.impl.purchase_requisition_dao_impl;
import dao.object_entry_dao;
import dao.purchase_requisition_dao;
import domain.Object_Entry;
import domain.Purchase_Requisition;
import domain.get_or_borrow_Requisition;
import domain.template_order;
import org.joda.time.DateTime;
import service.purchase_requisition_service;
import util.UuidUtil;

import java.text.SimpleDateFormat;
import java.util.List;

public class purchase_requisition_service_impl implements purchase_requisition_service {
    private purchase_requisition_dao purchaseRequisitionDao = new purchase_requisition_dao_impl();
    private object_entry_dao objectEntryDao = new object_entry_dao_impl();
    private SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String time = sfd.format(new java.util.Date());

    @Override
    public void createOrUpdate(template_order tando) {
        Purchase_Requisition table = (Purchase_Requisition) tando.getTable();
        List<Object_Entry> order = tando.getOrder();
        if (table.getPurchase_requisition_id().length() == 0) {
            createTable(table, order);
        }
        else {
            createTable(table, order);
        }
    }

    @Override
    public void createTable(Purchase_Requisition table, List<Object_Entry> order) {
        table.setRequisition_date(DateTime.parse(time));
        table.setPurchase_requisition_id(UuidUtil.getUuid());
        String orderId = UuidUtil.getUuid();
        table.setPurchase_order_id(orderId);
        for (Object_Entry object_entry : order) {
            object_entry.setObject_entry_id(UuidUtil.getUuid());
        }
        objectEntryDao.addEntry(order, orderId);
        purchaseRequisitionDao.createTable(table);
    }

    @Override
    public void changeTable(Purchase_Requisition table, List<Object_Entry> order) {
        purchaseRequisitionDao.updateTable(table);
        objectEntryDao.deleteEntryByOrder(table.getPurchase_order_id());
        for (Object_Entry object_entry : order) {
            object_entry.setObject_entry_id(UuidUtil.getUuid());
        }
        objectEntryDao.addEntry(order, table.getPurchase_order_id());
    }

    @Override
    public void approvalTable(Purchase_Requisition table) {
        table.setApproval_date(DateTime.parse(time));
        purchaseRequisitionDao.updateTable(table);
    }

    @Override
    public void deleteTable(String tableId) {
        Purchase_Requisition purchase_requisition = purchaseRequisitionDao.searchTableById(tableId);
        objectEntryDao.deleteEntryByOrder(purchase_requisition.getPurchase_order_id());
        purchaseRequisitionDao.deleteTable(tableId);
    }

    @Override
    public template_order searchTableById(String tableId) {
        Purchase_Requisition table = purchaseRequisitionDao.searchTableById(tableId);
        List<Object_Entry> order = objectEntryDao.search(table.getPurchase_order_id());
        return new template_order(table, order);
    }

    @Override
    public List<Purchase_Requisition> searchTableByUser(String userId) {
        return null;
    }

    @Override
    public List<Purchase_Requisition> searchTableByState(int state) {
        return null;
    }
}
