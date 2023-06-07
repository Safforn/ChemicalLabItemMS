package dao;

import domain.Purchase_Requisition;
import domain.get_or_borrow_Requisition;

import java.util.List;

public interface purchase_requisition_dao {
    /**
     * 新建申请单
     * @param table
     */
    boolean createTable(Purchase_Requisition table);

    /**
     * 更新申请单
     * @param table
     */
    boolean updateTable(Purchase_Requisition table);

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
    Purchase_Requisition searchTableById(String tableId);

    /**
     * 根据申请人查询申请单
     * @param userId
     * @return
     */
    List<Purchase_Requisition> searchTableByUser(String userId);

    /**
     * 审批人查询所有 已提交 未审批 的申请单
     * @return
     */
    List<Purchase_Requisition> searchTableByState(int state);

    /**
     * 查询所有未归还的采购申请单
     * @return
     */
    List<Purchase_Requisition> searchUnreturn();

    /**
     * 根据orderId修改订单状态
     * @param orderId
     * @param state
     * @return
     */
    boolean changeState(String orderId, int state);
}
