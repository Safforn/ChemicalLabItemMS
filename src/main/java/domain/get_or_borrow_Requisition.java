package domain;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

/**
 *用户实体类
 */
public class get_or_borrow_Requisition implements Serializable {
    private String get_or_borrow_requisition_id;//(PK)
    private String get_or_borrow_order_id;//
    private String applicant_user_id;//申领人(FK)
    private String approval_user_id;//审批人(FK)
    private String purpose;//领用用途
    private String position;//
    private String waste_reason;//
    private DateTime requisition_date;//
    private Date borrow_date;
    private Date return_date;
    private int state;//
    private String type;
    private String approval_opinions;//审批意见
    private DateTime approval_date;//

    /**
     *无参构造方法
     */
    public get_or_borrow_Requisition() {
    }

    /**
     * 有参构方法
     * @param get_or_borrow_requisition_id
     * @param get_or_borrow_order_id
     * @param applicant_user_id
     * @param approval_user_id
     * @param purpose
     * @param waste_reason
     * @param position
     * @param state
     * @param type
     * @param borrow_date
     * @param return_date
     * @param requisition_date
     * @param approval_opinions
     * @param approval_date
     */
    public get_or_borrow_Requisition(String get_or_borrow_requisition_id, String get_or_borrow_order_id, String applicant_user_id, String approval_user_id, String purpose, String position, String waste_reason, DateTime requisition_date, Date borrow_date, Date return_date, int state, String type, String approval_opinions, DateTime approval_date) {
        this.get_or_borrow_requisition_id = get_or_borrow_requisition_id;
        this.get_or_borrow_order_id = get_or_borrow_order_id;
        this.applicant_user_id = applicant_user_id;
        this.approval_user_id = approval_user_id;
        this.purpose = purpose;
        this.position = position;
        this.waste_reason = waste_reason;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getWaste_reason() {
        return waste_reason;
    }

    public void setWaste_reason(String waste_reason) {
        this.waste_reason = waste_reason;
    }

    public DateTime getRequisition_date() {
        return requisition_date;
    }

    public void setRequisition_date(DateTime requisition_date) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
