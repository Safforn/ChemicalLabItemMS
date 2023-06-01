package dao;

import domain.AddToWarehouse;
import domain.AddtoInfo;
import java.util.List;

public interface AddtoWarehouseDao {
    void recving(AddToWarehouse addToWarehouse);
    List<AddtoInfo> searchAll();
}
