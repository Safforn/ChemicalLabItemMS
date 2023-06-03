package domain;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 *采购申请实体类
 */
public class Purchase_Requisition implements Serializable {

    private String purchase_requisition_id;//采购申请表id(PK)
    private String purchase_order_id;//采购申请物品清单id
    private String approval_user_id;//审批人id（FK）
    private String requisition_user_id;//申请人id（FK）
    private int state;//申请表的状态（未提交、待审批、审批通过、审批未通过）
    private String purpose;//申请用途
    private DateTime requisition_date;//申请提交时间
    private String approval_opinions;//审批意见
    private DateTime approval_date;//审批时间


    /**
     *无参构造方法
     */
    public Purchase_Requisition() {
    }

    /**
     * 有参构方法
     * @param purchase_requisition_id
     * @param purchase_order_id
     * @param state
     * @param purpose
     * @param requisition_date
     * @param approval_opinions
     * @param approval_date
     * @param requisition_user_id
     * @param approval_user_id
     */
    public Purchase_Requisition(String purchase_requisition_id, String purchase_order_id, int state, String purpose, DateTime requisition_date, String approval_opinions, DateTime approval_date, String requisition_user_id, String approval_user_id) {
        this.purchase_requisition_id = purchase_requisition_id;
        this.purchase_order_id = purchase_order_id;
        this.state = state;
        this.purpose = purpose;
        this.requisition_date = requisition_date;
        this.approval_opinions = approval_opinions;
        this.approval_date = approval_date;
        this.requisition_user_id = requisition_user_id;
        this.approval_user_id = approval_user_id;
    }

    public String getPurchase_requisition_id() {
        return purchase_requisition_id;
    }

    public void setPurchase_requisition_id(String purchase_requisition_id) {
        this.purchase_requisition_id = purchase_requisition_id;
    }

    public String getPurchase_order_id() {
        return purchase_order_id;
    }

    public void setPurchase_order_id(String purchase_order_id) {
        this.purchase_order_id = purchase_order_id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public DateTime getRequisition_date() {
        return requisition_date;
    }

    public void setRequisition_date(DateTime requisition_date) {
        this.requisition_date = requisition_date;
    }

    public String getApproval_opinions() {
        return approval_opinions;
    }

    public void setApproval_opinions(String approval_opinions) {
        this.approval_opinions = approval_opinions;
    }

    public DateTime getApproval_date() {
        return approval_date;
    }

    public void setApproval_date(DateTime approval_date) {
        this.approval_date = approval_date;
    }


    public String getRequisition_user_id() {
        return requisition_user_id;
    }

    public void setRequisition_user_id(String requisition_user_id) {
        this.requisition_user_id = requisition_user_id;
    }

    public String getApproval_user_id() {
        return approval_user_id;
    }

    public void setApproval_user_id(String approval_user_id) {
        this.approval_user_id = approval_user_id;
    }
}
