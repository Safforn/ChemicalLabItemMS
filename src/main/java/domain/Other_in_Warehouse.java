package domain;

import org.joda.time.DateTime;

import java.io.Serializable;
/**
 *用户实体类
 */
public class Other_in_Warehouse implements Serializable {
    private String other_in_warehouse_id;//(PK)
    private String other_in_order_id;
    private String warehouse_id;//(FK)
    private String notes;
    private DateTime date;

    /**
     *无参构造方法
     */
    public Other_in_Warehouse() {
    }

    /**
     * 有参构方法
     * @param other_in_warehouse_id
     * @param notes
     * @param date
     * @param warehouse_id
     * @param other_in_order_id
     */
    public Other_in_Warehouse(String other_in_warehouse_id, String notes, DateTime date, String warehouse_id, String other_in_order_id) {
        this.other_in_warehouse_id = other_in_warehouse_id;
        this.notes = notes;
        this.date = date;
        this.warehouse_id = warehouse_id;
        this.other_in_order_id = other_in_order_id;
    }

    public String getOther_in_warehouse_id() {
        return other_in_warehouse_id;
    }

    public void setOther_in_warehouse_id(String other_in_warehouse_id) {
        this.other_in_warehouse_id = other_in_warehouse_id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public String getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(String warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public String getOther_in_order_id() {
        return other_in_order_id;
    }

    public void setOther_in_order_id(String other_in_order_id) {
        this.other_in_order_id = other_in_order_id;
    }
}
