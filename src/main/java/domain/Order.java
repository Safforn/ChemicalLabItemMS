package domain;

public class Order {
    private String oid;
    private String gid;
    private String uid;
    private Integer type;
    private String expressid;
    private String location;
    private String username;
    private String usertelephone;
    private String starttime;
    private Integer renttime;
    private Integer num;

    public Order() {

    }

    /**
     *
     * @param oid
     * @param gid
     * @param uid
     * @param type
     * @param expressid
     * @param location
     * @param username
     * @param usertelephone
     * @param starttime
     * @param renttime
     * @param num
     */
    public Order(String oid, String gid, String uid, Integer type, String expressid, String location,
                 String username, String usertelephone, String starttime, Integer renttime, Integer num) {
        this.oid = oid;
        this.gid = gid;
        this.uid = uid;
        this.type = type;
        this.expressid = expressid;
        this.location = location;
        this.username = username;
        this.usertelephone = usertelephone;
        this.starttime = starttime;
        this.renttime = renttime;
        this.num = num;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getExpressid() {
        return expressid;
    }

    public void setExpressid(String expressid) {
        this.expressid = expressid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsertelephone() {
        return usertelephone;
    }

    public void setUsertelephone(String usertelephone) {
        this.usertelephone = usertelephone;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public Integer getRenttime() {
        return renttime;
    }

    public void setRenttime(Integer renttime) {
        this.renttime = renttime;
    }
}
