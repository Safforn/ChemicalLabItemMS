package dao;
import domain.Object_Entry;
import java.util.List;

public interface object_entry_dao {
    /**
     * 新增订单
     * @param lists
     * @param orderId
     */
    boolean addEntry(List<Object_Entry> lists, String orderId);

    /**
     * 新增一行
     * @param orderId
     */
    boolean addOne(Object_Entry oe, String orderId);

    /**
     * 删除一条
     * @param id
     */
    boolean deleteEntryById(String id);

    /**
     * 按照订单编号删除
     * @param orderId
     */
    boolean deleteEntryByOrder(String orderId);

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
    boolean updateOne(String orderId, String objectId, int num);

    /**
     * 获取订单
     * @param orderId
     * @return
     */
    List<Object_Entry> search(String orderId);
}
