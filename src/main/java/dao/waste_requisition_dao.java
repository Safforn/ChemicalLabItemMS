package dao;

import domain.Purchase_Requisition;
import domain.Waste_Requisition;
import domain.get_or_borrow_Requisition;

import java.util.List;

public interface waste_requisition_dao {
    /**
     * 新建申请单
     * @param table
     */
    boolean createTable(Waste_Requisition table);

    /**
     * 更新申请单
     * @param table
     */
    boolean updateTable(Waste_Requisition table);

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
    Waste_Requisition searchTableById(String tableId);

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


    boolean updateByApprove(Waste_Requisition table);

}
