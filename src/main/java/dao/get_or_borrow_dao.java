package dao;

import domain.get_or_borrow_Requisition;

import java.util.List;

public interface get_or_borrow_dao {
    /**
     * 新建申请单
     * @param table
     */
    void createTable(get_or_borrow_Requisition table);

    /**
     * 更新申请单
     * @param table
     */
    void updateTable(get_or_borrow_Requisition table);

    /**
     * 删除申请单
     * @param tableId
     */
    void deleteTable(String tableId);

    /**
     * 提交申请单
     * @param tableId
     */
    void submitTable(String tableId);

    /**
     * 根据id查询单个申请单
     * @param tableId
     * @return
     */
    get_or_borrow_Requisition searchTableById(String tableId);

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
}
