package domain;

import java.io.Serializable;

/**
 *身份表实体类
 */
public class Identity implements Serializable {

    private String identity_id;//身份表id(PK)
    private String user_id;//用户id(FK)
    private int identity;//身份（审批人员、实验室管理员、仓库管理员、领用借用申请人、废弃物管理员）

    /**
     *无参构造方法
     */
    public Identity() {}

    /**
     * 有参构方法
     * @param identity_id
     * @param user_id
     * @param identity
     */
    public Identity(String identity_id, String user_id, int identity) {
        this.identity_id = identity_id;
        this.user_id = user_id;
        this.identity = identity;
    }

    public Identity(String u) {
    }

    public String getIdentity_id() {
        return identity_id;
    }

    public void setIdentity_id(String identity_id) {
        this.identity_id = identity_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }
}
