package domain;

import org.joda.time.DateTime;

import java.io.Serializable;
/**
 *废弃物申请实体类
 */
public class Waste_Requisition implements Serializable {

    private String waste_requisition_id;//废弃申请表id(PK)
    private String waste_order_id;//废弃物品清单id
    private String requisition_user_id;//申请人(FK)
    private String approval_user_id;//审批人(FK)
    private String waste_user_id;//废弃物管理员(FK)
    private String waste_reason;//废弃原因
    private String position;//废弃物存放位置
    private int state;//申请表的状态（未提交、待审批、审批通过、审批未通过）
    private DateTime requisition_date;//申请提交时间
    private String approval_opinions;//审批意见
    private DateTime approval_date;//审批时间

    /**
     *无参构造方法
     */
    public Waste_Requisition() {
    }

    /**
     * 有参构方法
     * @param waste_requisition_id
     * @param waste_order_id
     * @param requisition_user_id
     * @param approval_user_id
     * @param waste_user_id
     * @param waste_reason
     * @param position
     * @param state
     * @param requisition_date
     * @param approval_opinions
     * @param approval_date
     */
    public Waste_Requisition(String waste_requisition_id, String waste_order_id, String requisition_user_id, String approval_user_id, String waste_user_id, String waste_reason, String position, int state, DateTime requisition_date, String approval_opinions, DateTime approval_date) {
        this.waste_requisition_id = waste_requisition_id;
        this.waste_order_id = waste_order_id;
        this.requisition_user_id = requisition_user_id;
        this.approval_user_id = approval_user_id;
        this.waste_user_id = waste_user_id;
        this.waste_reason = waste_reason;
        this.position = position;
        this.state = state;
        this.requisition_date = requisition_date;
        this.approval_opinions = approval_opinions;
        this.approval_date = approval_date;
    }

    public String getWaste_requisition_id() {
        return waste_requisition_id;
    }

    public void setWaste_requisition_id(String waste_requisition_id) {
        this.waste_requisition_id = waste_requisition_id;
    }

    public String getWaste_order_id() {
        return waste_order_id;
    }

    public void setWaste_order_id(String waste_order_id) {
        this.waste_order_id = waste_order_id;
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

    public String getWaste_user_id() {
        return waste_user_id;
    }

    public void setWaste_user_id(String waste_user_id) {
        this.waste_user_id = waste_user_id;
    }

    public String getWaste_reason() {
        return waste_reason;
    }

    public void setWaste_reason(String waste_reason) {
        this.waste_reason = waste_reason;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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
}
