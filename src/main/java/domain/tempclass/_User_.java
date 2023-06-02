package domain;

import java.io.Serializable;

/**
 * 用户实体类
 */
public class _User_ implements Serializable {
    private String uid;//用户id
    private String name;//用户名，账号
    private String password;//密码
    private String telephone;//手机号

    /**
     * 无参构造方法
     */
    public _User_() {
    }

    /**
     * 有参构方法
     * @param uid
     * @param name
     * @param password
     * @param telephone
     */
    public _User_(String uid, String name, String password, String telephone) {
        this.uid = uid;
        this.name = name;
        this.password = password;
        this.telephone = telephone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}
