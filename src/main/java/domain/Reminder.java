package domain;

import java.io.Serializable;
/**
 *催还单实体类
 */
public class Reminder implements Serializable {
     private String id;//催还单id(PK)
     private String get_or_borrow_requisition_id;//借用申请表id(FK)

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
