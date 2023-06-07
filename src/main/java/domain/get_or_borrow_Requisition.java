package domain;

import java.io.Serializable;
import java.util.Date;

/**
 *领用/借用申请实体类
 */
public class get_or_borrow_Requisition implements Serializable {
    private String get_or_borrow_requisition_id;//领用/借用申请表id(PK)
    private String get_or_borrow_order_id;//领用/借用申请物品清单id
    private String applicant_user_id;//申领人(FK)
    private String approval_user_id;//审批人(FK)
    private String purpose;//领用用途
    private Date requisition_date;//申请提交时间
    private Date borrow_date;//借用/领用日期
    private Date return_date;//归还日期
    private int state;//申请表的状态（未提交、待审批、审批通过、审批未通过）
    private int type;//类型（借用/领用）
    private String approval_opinions;//审批意见
    private Date approval_date;//审批的时间

    /**
     *无参构造方法
     */
    public get_or_borrow_Requisition() {}

    /**
     * 有参构方法
     * @param get_or_borrow_requisition_id
     * @param get_or_borrow_order_id
     * @param applicant_user_id
     * @param approval_user_id
     * @param purpose
     * @param state
     * @param type
     * @param borrow_date
     * @param return_date
     * @param requisition_date
     * @param approval_opinions
     * @param approval_date
     */
    public get_or_borrow_Requisition(String get_or_borrow_requisition_id, String get_or_borrow_order_id, String applicant_user_id, String approval_user_id, String purpose, Date requisition_date, Date borrow_date, Date return_date, int state, int type, String approval_opinions, Date approval_date) {
        this.get_or_borrow_requisition_id = get_or_borrow_requisition_id;
        this.get_or_borrow_order_id = get_or_borrow_order_id;
        this.applicant_user_id = applicant_user_id;
        this.approval_user_id = approval_user_id;
        this.purpose = purpose;
        this.requisition_date = requisition_date;
        this.borrow_date = borrow_date;
        this.return_date = return_date;
        this.state = state;
        this.type = type;
        this.approval_opinions = approval_opinions;
        this.approval_date = approval_date;
    }

    public String getGet_or_borrow_requisition_id() {
        return get_or_borrow_requisition_id;
    }

    public void setGet_or_borrow_requisition_id(String get_or_borrow_requisition_id) {
        this.get_or_borrow_requisition_id = get_or_borrow_requisition_id;
    }

    public String getGet_or_borrow_order_id() {
        return get_or_borrow_order_id;
    }

    public void setGet_or_borrow_order_id(String get_or_borrow_order_id) {
        this.get_or_borrow_order_id = get_or_borrow_order_id;
    }

    public String getApplicant_user_id() {
        return applicant_user_id;
    }

    public void setApplicant_user_id(String applicant_user_id) {
        this.applicant_user_id = applicant_user_id;
    }

    public String getApproval_user_id() {
        return approval_user_id;
    }

    public void setApproval_user_id(String approval_user_id) {
        this.approval_user_id = approval_user_id;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Date getRequisition_date() {
        return requisition_date;
    }

    public void setRequisition_date(Date requisition_date) {
        this.requisition_date = requisition_date;
    }

    public Date getBorrow_date() {
        return borrow_date;
    }

    public void setBorrow_date(Date borrow_date) {
        this.borrow_date = borrow_date;
    }

    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getApproval_opinions() {
        return approval_opinions;
    }

    public void setApproval_opinions(String approval_opinions) {
        this.approval_opinions = approval_opinions;
    }

    public Date getApproval_date() {
        return approval_date;
    }

    public void setApproval_date(Date approval_date) {
        this.approval_date = approval_date;
    }
}
