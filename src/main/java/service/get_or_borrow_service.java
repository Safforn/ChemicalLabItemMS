package service;

import domain.Object_Entry;
import domain.get_or_borrow_Requisition;
import domain.template_order;

import java.util.List;

public interface get_or_borrow_service {
    /**
     * 创建或修改使用申请单
     * @param tando
     * @return
     */
    boolean createOrUpdate(template_order tando);

    /**
     * 审批人审批申请单
     * @param table
     */
    boolean approvalTable(get_or_borrow_Requisition table);

    /**
     * 删除申请单
     * @param tableId
     */
    boolean deleteTable(String tableId);

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
    List<get_or_borrow_Requisition> searchTableByUser(String userId);

    /**
     * 审批人查询所有 已提交 未审批 的申请单
     * @return
     */
    List<get_or_borrow_Requisition> searchTableByState(int state);

    /**
     * 查询已提交未归还的借用单
     * @return
     */
    List<get_or_borrow_Requisition> searchBorrowUnreturn();
}
