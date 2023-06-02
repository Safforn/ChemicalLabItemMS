package domain;

import org.joda.time.DateTime;

import java.io.Serializable;
/**
 *用户实体类
 */
public class Ex_Warehouse implements Serializable {
    private String ex_warehouse_id;//(PK)
    private String ex_order_id;
    private String warehouse_id;//(FK)
    private String requisition_id;//(FK)
    private String notes;
    private DateTime date;

    /**
     *无参构造方法
     */
    public Ex_Warehouse() {
    }

    /**
     * 有参构方法
     * @param ex_warehouse_id
     * @param notes
     * @param date
     * @param warehouse_id
     * @param ex_order_id
     * @param requisition_id
     */
    public Ex_Warehouse(String ex_warehouse_id, String notes, DateTime date, String warehouse_id, String ex_order_id, String requisition_id) {
        this.ex_warehouse_id = ex_warehouse_id;
        this.notes = notes;
        this.date = date;
        this.warehouse_id = warehouse_id;
        this.ex_order_id = ex_order_id;
        this.requisition_id = requisition_id;
    }

    public String getEx_warehouse_id() {
        return ex_warehouse_id;
    }

    public void setEx_warehouse_id(String ex_warehouse_id) {
        this.ex_warehouse_id = ex_warehouse_id;
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

    public String getEx_order_id() {
        return ex_order_id;
    }

    public void setEx_order_id(String ex_order_id) {
        this.ex_order_id = ex_order_id;
    }

    public String getRequisition_id() {
        return requisition_id;
    }

    public void setRequisition_id(String requisition_id) {
        this.requisition_id = requisition_id;
    }
}
