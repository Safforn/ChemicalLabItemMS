package domain;

import java.io.Serializable;
import java.util.List;


public class template_order<T> implements Serializable {

    private T table;  // 申请表

    private List<Object_Entry> order;  // 物品清单行 数组

    public template_order(T table, List<Object_Entry> order) {
        this.table = table;
        this.order = order;
    }

    public template_order() {

    }


    public List<Object_Entry> getOrder() {
        return order;
    }

    public void setOrder(List<Object_Entry> order) {
        this.order = order;
    }


    public T getTable() {
        return table;
    }

    public void setTable(T table) {
        this.table = table;
    }


}
