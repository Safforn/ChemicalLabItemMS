package domain;

import java.io.Serializable;
import java.util.List;

public class get_or_borrow_and_order implements Serializable {

    private get_or_borrow_Requisition table;

    private List<Object_Entry> order;

    public get_or_borrow_and_order(get_or_borrow_Requisition table, List<Object_Entry> order) {
        this.table = table;
        this.order = order;
    }


    public List<Object_Entry> getOrder() {
        return order;
    }

    public void setOrder(List<Object_Entry> order) {
        this.order = order;
    }


    public get_or_borrow_Requisition getTable() {
        return table;
    }

    public void setTable(get_or_borrow_Requisition table) {
        this.table = table;
    }


}
