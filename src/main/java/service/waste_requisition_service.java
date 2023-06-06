package service;

import domain.Object_Entry;
import domain.Waste_Requisition;
import domain.get_or_borrow_Requisition;
import domain.template_order;

import java.util.List;

public interface waste_requisition_service {

    void createOrUpdate(template_order tando);
    /**
     * 新建申请单
     * @param table
     */
    void createTable(Waste_Requisition table, List<Object_Entry> order);

    /**
     * 申请人修改申请单
     * @param table
     */
    void changeTable(Waste_Requisition table, List<Object_Entry> order);

    /**
     * 审批人审批申请单
     * @param table
     */
    void approvalTable(Waste_Requisition table);

    /**
     * 删除申请单
     * @param tableId
     */
    void deleteTable(String tableId);

//    /**
//     * 提交申请单
//     * @param tableId
//     */
//    void submitTable(String tableId);

    /**
     * 根据id查询单个申请单
     * @param tableId
     * @return
     */
    template_order searchTableById(String tableId);

    /**
     * 根据申请人查询申请单
     * @param userId
     * @return
     */
    List<Waste_Requisition> searchTableByUser(String userId);

    /**
     * 审批人查询所有 已提交 未审批 的申请单
     * @return
     */
    List<Waste_Requisition> searchTableByState(int state);
}
