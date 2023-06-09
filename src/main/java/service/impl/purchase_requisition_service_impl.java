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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import service.purchase_requisition_service;
import util.UuidUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static util.UuidUtil.getCurrentTime;

public class purchase_requisition_service_impl implements purchase_requisition_service {
    private purchase_requisition_dao purchaseRequisitionDao = new purchase_requisition_dao_impl();
    private object_entry_dao objectEntryDao = new object_entry_dao_impl();

    @Override
    public boolean createOrUpdate(template_order tando) {
        Purchase_Requisition table = (Purchase_Requisition) tando.getTable();
        List<Object_Entry> order = tando.getOrder();
        if (table.getPurchase_requisition_id() == null) {
            return createTable(table, order);
        }
        else {
            return changeTable(table, order);
        }
    }

    /**
     * 创建采购申请单
     * @param table
     * @param order
     * @return
     */
    private boolean createTable(Purchase_Requisition table, List<Object_Entry> order) {
        System.out.println("进入CreateTable");
        table.setRequisition_date(getCurrentTime());
        table.setPurchase_requisition_id(UuidUtil.getPR());
//        String orderId = UuidUtil.getUuid();
//        table.setPurchase_order_id(orderId);

        System.out.println("-------CreateTable--------");
        table.print();

        for (Object_Entry object_entry : order) {
            object_entry.setObject_entry_id(UuidUtil.getOE());
        }
        return objectEntryDao.addEntry(order, table.getPurchase_order_id()) && purchaseRequisitionDao.createTable(table);
    }

    /**
     * 修改采购申请单
     * @param table
     * @param order
     * @return
     */
    private boolean changeTable(Purchase_Requisition table, List<Object_Entry> order) {
        System.out.println("====Purchase_Requisition  changeTable====");
        System.out.println("Service : " + table.getPurchase_requisition_id());
        if (purchaseRequisitionDao.updateTable(table)) {
            if (!objectEntryDao.deleteEntryByOrder(table.getPurchase_order_id())) {
                return false;
            }
            for (Object_Entry object_entry : order) {
                object_entry.setObject_entry_id(UuidUtil.getOE());
            }
            return objectEntryDao.addEntry(order, table.getPurchase_order_id());
        }
        return false;
    }

    @Override
    public boolean approvalTable(Purchase_Requisition table) {
        table.setRequisition_date(getCurrentTime());
        return purchaseRequisitionDao.updateTable(table);
    }

    @Override
    public boolean deleteTable(String tableId) {
        Purchase_Requisition purchase_requisition = purchaseRequisitionDao.searchTableById(tableId);
        return objectEntryDao.deleteEntryByOrder(purchase_requisition.getPurchase_order_id()) && purchaseRequisitionDao.deleteTable(tableId);
    }

    @Override
    public template_order searchTableById(String tableId) {
        Purchase_Requisition table = purchaseRequisitionDao.searchTableById(tableId);
        List<Object_Entry> order = objectEntryDao.search(table.getPurchase_order_id());
        return new template_order(table, order);
    }

    @Override
    public List<Purchase_Requisition> searchTableByUser(String userId) {
        return purchaseRequisitionDao.searchTableByUser(userId);
    }

    @Override
    public List<Purchase_Requisition> searchTableByState(int state) {
        return purchaseRequisitionDao.searchTableByState(state);
    }


    @Override
    public List<Purchase_Requisition> searchUnreturn() {
        return purchaseRequisitionDao.searchUnreturn();
    }

    //purchase_requisition_id=PR-0000002&state=2&approval_user_id=0005&approval_opinions=朕允了
    public void updateByApprove(Purchase_Requisition purchase_requisition) {
        purchaseRequisitionDao.updateByApprove(purchase_requisition);
    }
}
