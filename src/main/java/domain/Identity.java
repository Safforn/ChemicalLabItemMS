package domain;

import java.io.Serializable;

/**
 *用户实体类
 */
public class Identity implements Serializable {

    private String identity_id;//(PK)
    private String user_id;//(FK)
    private int identity;

    /**
     *无参构造方法
     */
    public Identity() {
    }

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
