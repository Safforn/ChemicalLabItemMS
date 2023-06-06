package domain;
import java.io.Serializable;
import org.joda.time.DateTime;
/**
 *采购入库实体类
 */
public class Purchase_in_Warehouse implements Serializable {
    private String purchase_in_warehouse_id;//采购入库登记表id（PK）
    private String warehouse_id;//仓库编号（FK）
    private String purchase_in_order_id;//采购入库物品清单的表头，用于归类标识该清单内采购入库的物品行
    private String purchase_order_id;//被入库的采购物品清单id（FK）
    private String notes;//备注
    private int type;
    private DateTime date;//入库时间

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /**
     *无参构造方法
     */
    public Purchase_in_Warehouse() {}

    /**
     * 有参构方法
     * @param purchase_in_warehouse_id
     * @param notes
     * @param date
     * @param warehouse_id
     * @param purchase_in_order_id
     * @param purchase_order_id
     * @param type
     */
    public Purchase_in_Warehouse(String purchase_in_warehouse_id, String notes, DateTime date, String warehouse_id, String purchase_in_order_id, String purchase_order_id, int type) {
        this.purchase_in_warehouse_id = purchase_in_warehouse_id;
        this.notes = notes;
        this.date = date;
        this.warehouse_id = warehouse_id;
        this.purchase_in_order_id = purchase_in_order_id;
        this.purchase_order_id = purchase_order_id;
        this.type = type;
    }

    public String getPurchase_in_warehouse_id() {
        return purchase_in_warehouse_id;
    }

    public void setPurchase_in_warehouse_id(String purchase_in_warehouse_id) {
        this.purchase_in_warehouse_id = purchase_in_warehouse_id;
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

    public String getPurchase_in_order_id() {
        return purchase_in_order_id;
    }

    public void setPurchase_in_order_id(String purchase_in_order_id) {
        this.purchase_in_order_id = purchase_in_order_id;
    }

    public String getPurchase_order_id() {
        return purchase_order_id;
    }

    public void setPurchase_order_id(String purchase_order_id) {
        this.purchase_order_id = purchase_order_id;
    }
}
