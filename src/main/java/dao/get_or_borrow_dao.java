package dao;

import domain.Purchase_Requisition;
import domain.get_or_borrow_Requisition;

import java.util.List;

public interface get_or_borrow_dao {
    /**
     * 新建申请单
     * @param table
     */
    boolean createTable(get_or_borrow_Requisition table);

    /**
     * 更新申请单
     * @param table
     */
    boolean updateTable(get_or_borrow_Requisition table);

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
     * @return get_or_borrow_Requisition
     */
    get_or_borrow_Requisition searchTableById(String tableId);

    /**
     * 根据orderid查询单个申请单
     * @param orderId
     * @return get_or_borrow_Requisition
     */
    get_or_borrow_Requisition searchTableByOrderId(String orderId);


    /**
     * 根据申请人查询申请单
     * @param userId
     * @return List<get_or_borrow_Requisition>
     */
    List<get_or_borrow_Requisition> searchTableByUser(String userId);

    /**
     * 审批人查询所有 已提交 未审批 的申请单
     * @return List<get_or_borrow_Requisition>
     */
    List<get_or_borrow_Requisition> searchTableByState(int state);

    /**
     * 查询已提交未归还的借用单
     * @return List<get_or_borrow_Requisition>
     */
    List<get_or_borrow_Requisition> searchBorrowUnreturn();

    boolean updateByApprove(get_or_borrow_Requisition table);
}
