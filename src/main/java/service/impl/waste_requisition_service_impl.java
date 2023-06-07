package service.impl;

import dao.impl.object_entry_dao_impl;
import dao.impl.waste_requisition_dao_impl;
import dao.object_entry_dao;
import dao.waste_requisition_dao;
import domain.Object_Entry;
import domain.Waste_Requisition;
import domain.get_or_borrow_Requisition;
import domain.template_order;
import org.joda.time.DateTime;
import service.waste_requisition_service;
import util.UuidUtil;

import java.text.SimpleDateFormat;
import java.util.List;

public class waste_requisition_service_impl implements waste_requisition_service {
    private waste_requisition_dao wasteRequisitionDao = new waste_requisition_dao_impl();
    private object_entry_dao objectEntryDao = new object_entry_dao_impl();
    private SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String time = sfd.format(new java.util.Date());
    @Override
    public boolean createOrUpdate(template_order tando) {
        Waste_Requisition table = (Waste_Requisition) tando.getTable();
        List<Object_Entry> order = tando.getOrder();
        if (table.getWaste_requisition_id().length() == 0) {
            return createTable(table, order);
        }
        else {
            return changeTable(table, order);
        }
    }

    private boolean createTable(Waste_Requisition table, List<Object_Entry> order) {
        table.setWaste_requisition_id(UuidUtil.getUuid());
//        table.setRequisition_date(DateTime.parse(time));
        String orderId = UuidUtil.getUuid();
        table.setWaste_order_id(orderId);
        for (Object_Entry object_entry : order) {
            object_entry.setObject_entry_id(UuidUtil.getUuid());
        }
        return objectEntryDao.addEntry(order, orderId) && wasteRequisitionDao.createTable(table);
    }

    private boolean changeTable(Waste_Requisition table, List<Object_Entry> order) {
        if (!wasteRequisitionDao.updateTable(table)) {
            return false;
        }
        if (!objectEntryDao.deleteEntryByOrder(table.getWaste_order_id())) {
            return false;
        }
        for (Object_Entry object_entry : order) {
            object_entry.setObject_entry_id(UuidUtil.getUuid());
        }
        return objectEntryDao.addEntry(order, table.getWaste_order_id());
    }

    @Override
    public boolean approvalTable(Waste_Requisition table) {
//        table.setApproval_date(DateTime.parse(time));
        return wasteRequisitionDao.updateTable(table);
    }

    @Override
    public boolean deleteTable(String tableId) {
        Waste_Requisition table = wasteRequisitionDao.searchTableById(tableId);
        return objectEntryDao.deleteEntryByOrder(table.getWaste_order_id()) && wasteRequisitionDao.deleteTable(tableId);
    }

    @Override
    public template_order searchTableById(String tableId) {
        Waste_Requisition table = wasteRequisitionDao.searchTableById(tableId);
        List<Object_Entry> order = objectEntryDao.search(table.getWaste_order_id());
        return new template_order(table, order);
    }

    @Override
    public List<Waste_Requisition> searchTableByUser(String userId) {
        return wasteRequisitionDao.searchTableByUser(userId);
    }

    @Override
    public List<Waste_Requisition> searchTableByState(int state) {
        return wasteRequisitionDao.searchTableByState(state);
    }
}
