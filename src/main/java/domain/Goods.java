package domain;

public class Goods {
    private String gid;
    private String name;
    private String type;
    private double price;
    private String picture;
    private Integer num;
    private String description;
    private String location;
    private Integer rentorbuy;
    public Goods() {

    }

    /**
     *
     * @param gid
     * @param name
     * @param type
     * @param price
     * @param picture
     * @param num
     * @param description
     * @param location
     * @param rentorbuy
     */
    public Goods(String gid, String name, String type, double price, String picture, Integer num, String description, String location, Integer rentorbuy) {
        this.gid = gid;
        this.name = name;
        this.type = type;
        this.price = price;
        this.picture = picture;
        this.num = num;
        this.description = description;
        this.location = location;
        this.rentorbuy = rentorbuy;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getRentorbuy() {
        return rentorbuy;
    }

    public void setRentorbuy(Integer rentorbuy) {
        this.rentorbuy = rentorbuy;
    }
}
