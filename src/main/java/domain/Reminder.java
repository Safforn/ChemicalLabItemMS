package domain;

import java.io.Serializable;
/**
 *用户实体类
 */
public class Reminder implements Serializable {
     private String id;//(PK)
     private String get_or_borrow_requisition_id;//(FK)

    /**
     *无参构造方法
     */
    public Reminder() {
    }

    /**
     * 有参构方法
     * @param id
     * @param get_or_borrow_requisition_id
     */
    public Reminder(String id, String get_or_borrow_requisition_id) {
        this.id = id;
        this.get_or_borrow_requisition_id = get_or_borrow_requisition_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGet_or_borrow_requisition_id() {
        return get_or_borrow_requisition_id;
    }

    public void setGet_or_borrow_requisition_id(String get_or_borrow_requisition_id) {
        this.get_or_borrow_requisition_id = get_or_borrow_requisition_id;
    }
}
