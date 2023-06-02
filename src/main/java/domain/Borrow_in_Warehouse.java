package domain;
import org.joda.time.DateTime;
import java.io.Serializable;

/**
 *用户实体类
 */
public class Borrow_in_Warehouse implements Serializable {
    private String borrow_in_warehouse_id;//(PK)
    private String borrow_in_order_id;
    private String warehouse_id;//(FK)
    private String get_or_borrow_order_id;//(FK)
    private String notes;
    private DateTime date;

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
    public Borrow_in_Warehouse(String borrow_in_warehouse_id, String notes, DateTime date, String warehouse_id, String borrow_in_order_id, String get_or_borrow_order_id) {
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
