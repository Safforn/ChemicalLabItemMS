package domain;

import java.io.Serializable;
import java.util.Date;
/**
 *用户实体类
 */
public class Object implements Serializable {

    private String object_id;//(PK)
    private String name;//
    private String specification;//
    private float quantity;//数量
    private String unit;//单位
    private String classification;//
    private float price ;//
    private Date expiration_time;//过期时间
    private float lower_limit;//
    private float upper_limit;//
    private String notes;//

    /**
     *无参构造方法
     */
    public Object() {
    }

    /**
     * 有参构方法
     * @param object_id
     * @param name
     * @param lower_limit
     * @param upper_limit
     * @param specification
     * @param classification
     * @param price
     * @param quantity
     * @param unit
     * @param expiration_time
     * @param notes
     */
    public Object(String object_id, String name, float lower_limit, float upper_limit, String specification, String classification, float price, float quantity, String unit, Date expiration_time, String notes) {
        this.object_id = object_id;
        this.name = name;
        this.lower_limit = lower_limit;
        this.upper_limit = upper_limit;
        this.specification = specification;
        this.classification = classification;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
        this.expiration_time = expiration_time;
        this.notes = notes;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLower_limit() {
        return lower_limit;
    }

    public void setLower_limit(float lower_limit) {
        this.lower_limit = lower_limit;
    }

    public float getUpper_limit() {
        return upper_limit;
    }

    public void setUpper_limit(float upper_limit) {
        this.upper_limit = upper_limit;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getExpiration_time() {
        return expiration_time;
    }

    public void setExpiration_time(Date expiration_time) {
        this.expiration_time = expiration_time;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
