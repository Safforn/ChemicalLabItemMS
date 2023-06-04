package service.service_temp.impl;

import dao.dao_temp.ExwarehouseDao;
import dao.dao_temp.impl.ExwarehouseDaoImpl;
import domain.ExInfo;
import service.service_temp.ExWarehouseService;

import java.util.List;

public class ExWarehouseServiceImpl implements ExWarehouseService {
    ExwarehouseDao exwarehouseDao = new ExwarehouseDaoImpl();

    /**
     *
     * @return
     */
    @Override
    public List<ExInfo> searchAllEx() {
        return exwarehouseDao.searchAll();
    }
}
