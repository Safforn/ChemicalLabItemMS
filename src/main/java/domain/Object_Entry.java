package domain;

import java.io.Serializable;

/**
 *物品行实体类
 */
public class  Object_Entry implements Serializable {
    private String object_entry_id;//物品行id(PK)
    private String order_id;//物品清单id（借用入库物品清单、出库物品清单、领用借用物品清单、其他入库物品清单、采购入库物品清单、采购申请物品清单、废弃物申请物品清单）(FK)
    private String object_id;//物品id(FK)

    /**
     *无参构造方法
     */
    public Object_Entry() {
    }

    /**
     * 有参构方法
     * @param object_entry_id
     * @param order_id
     * @param object_id
     */
    public Object_Entry(String object_entry_id, String order_id, String object_id) {
        this.object_entry_id = object_entry_id;
        this.order_id = order_id;
        this.object_id = object_id;
    }


    public String getObject_entry_id() {
        return object_entry_id;
    }

    public void setObject_entry_id(String object_entry_id) {
        this.object_entry_id = object_entry_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }
}
