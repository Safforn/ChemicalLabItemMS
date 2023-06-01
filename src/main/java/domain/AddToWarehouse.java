package domain;

import javax.xml.crypto.Data;

public class AddToWarehouse {
    private String aid;
    private String uid;
    private String gid;
    private String time;
    private int num;
    public AddToWarehouse() {}

    /**
     *
     * @param aid
     * @param uid
     * @param gid
     * @param time
     * @param num
     */
    public AddToWarehouse(String aid, String uid, String gid, String time, int num) {
        this.aid = aid;
        this.uid = uid;
        this.gid = gid;
        this.time = time;
        this.num = num;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
