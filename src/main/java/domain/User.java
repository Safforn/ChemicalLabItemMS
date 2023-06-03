package domain;

import org.joda.time.DateTime;

import java.io.Serializable;
/**
 *用户实体类
 */
public class User implements Serializable {

    private String user_id;//用户id(PK)
    private String account;//账号
    private String email;//电子邮件
    private String password;//密码
    private String name;//姓名
    private String phonenumber;//电话号码

    /**
     *无参构造方法
     */
    public User() {
    }

    /**
     * 有参构方法
     * @param user_id
     * @param account
     * @param email
     * @param password
     * @param name
     * @param phonenumber
     */
    public User(String user_id, String account, String email, String password, String name, String phonenumber) {
        this.user_id = user_id;
        this.account = account;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phonenumber = phonenumber;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
