package domain;

public class ExWarehouse {
    private String eid;
    private String oid;
    private String uid;
    public ExWarehouse() {

    }

    /**
     *
     * @param eid
     * @param oid
     * @param uid
     */
    public ExWarehouse(String eid, String oid, String uid) {
        this.eid = eid;
        this.oid = oid;
        this.uid = uid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
