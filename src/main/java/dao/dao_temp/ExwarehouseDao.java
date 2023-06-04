package dao.dao_temp;

import domain.ExInfo;
import domain.ExWarehouse;

import java.util.List;

public interface ExwarehouseDao {
    void exhausting(ExWarehouse exWarehouse);
    List<ExInfo> searchAll();
}
