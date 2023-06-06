package dao;

import domain.Object_Entry;

import java.util.List;

public interface object_entry_dao {
    /**
     * 新增订单
     * @param lists
     * @param orderId
     */
    void addEntry(List<Object_Entry> lists, String orderId);

    /**
     * 新增一行
     * @param orderId
     */
    void addOne(Object_Entry oe, String orderId);

    /**
     * 删除一条
     * @param id
     */
    void deleteEntryById(String id);

    /**
     * 按照订单编号删除
     * @param orderId
     */
    void deleteEntryByOrder(String orderId);

    /**
     * 查询一行是否存在
     * @param orderId
     * @param objectId
     * @return
     */
    int findOne(String orderId, String objectId);

    /**
     * 修改一行的数量
     * @param orderId
     * @param objectId
     * @param num
     */
    void updateOne(String orderId, String objectId, int num);

    /**
     * 获取订单
     * @param orderId
     * @return
     */
    List<Object_Entry> search(String orderId);
}
