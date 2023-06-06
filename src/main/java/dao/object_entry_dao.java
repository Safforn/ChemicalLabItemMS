package dao;

import domain.Object_Entry;

import java.util.List;

public interface object_entry_dao {
    /**
     * 新增
     * @param lists
     * @param orderId
     */
    void addEntry(List<Object_Entry> lists, String orderId);

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

    List<Object_Entry> search(String orderId);
}
