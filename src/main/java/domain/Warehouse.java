package domain;
import java.io.Serializable;

/**
 *仓库实体类
 */
public class Warehouse implements Serializable {
    private String warehouse_id;//仓库id（PK）
    private String warehouse_keeper_user_id;//仓库管理员id（FK）
    private String name;//姓名
    private int level;//火灾危险性类别等级（甲-乙-丙-丁-戊-普通）甲最高（管理最严格） 戊最低 普通仓库存放其他一般物品

   /**
    *无参构造方法
    */
    public Warehouse(){
    }

    /**
     * 有参构方法
     * @param warehouse_id
     * @param warehouse_keeper_user_id
     * @param name
     * @param level
     */
    public Warehouse(String warehouse_id, String warehouse_keeper_user_id, String name, int level) {
        this.warehouse_id = warehouse_id;
        this.warehouse_keeper_user_id = warehouse_keeper_user_id;
        this.name = name;
        this.level = level;
    }

    public String getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(String warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public String getWarehouse_keeper_user_id() {
        return warehouse_keeper_user_id;
    }

    public void setWarehouse_keeper_user_id(String warehouse_keeper_user_id) {
        this.warehouse_keeper_user_id = warehouse_keeper_user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
