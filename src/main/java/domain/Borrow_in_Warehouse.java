package domain;
import java.io.Serializable;
import java.util.Date;

/**
 *借用入库实体类
 */
public class Borrow_in_Warehouse implements Serializable {
    private String borrow_in_warehouse_id;//借用入库登记表id(PK)
    private String borrow_in_order_id; //借用入库物品清单id
    private String warehouse_id;//仓库编号id(FK)
    private String get_or_borrow_order_id;//领用借用申请清单id(FK)
    private String notes; //备注
    private Date date; //入库日期

    /**
     *无参构造方法
     */
    public Borrow_in_Warehouse() {
    }

    /**
     * 有参构方法
     * @param borrow_in_warehouse_id
     * @param notes
     * @param date
     * @param warehouse_id
     * @param borrow_in_order_id
     * @param get_or_borrow_order_id
     */
    public Borrow_in_Warehouse(String borrow_in_warehouse_id, String notes, Date date, String warehouse_id, String borrow_in_order_id, String get_or_borrow_order_id) {
        this.borrow_in_warehouse_id = borrow_in_warehouse_id;
        this.notes = notes;
        this.date = date;
        this.warehouse_id = warehouse_id;
        this.borrow_in_order_id = borrow_in_order_id;
        this.get_or_borrow_order_id = get_or_borrow_order_id;
    }

    public String getBorrow_in_warehouse_id() {
        return borrow_in_warehouse_id;
    }

    public void setBorrow_in_warehouse_id(String borrow_in_warehouse_id) {
        this.borrow_in_warehouse_id = borrow_in_warehouse_id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(String warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public String getBorrow_in_order_id() {
        return borrow_in_order_id;
    }

    public void setBorrow_in_order_id(String borrow_in_order_id) {
        this.borrow_in_order_id = borrow_in_order_id;
    }

    public String getGet_or_borrow_order_id() {
        return get_or_borrow_order_id;
    }

    public void setGet_or_borrow_order_id(String get_or_borrow_order_id) {
        this.get_or_borrow_order_id = get_or_borrow_order_id;
    }
}
