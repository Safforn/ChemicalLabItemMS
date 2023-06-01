package service.impl;

import dao.ExwarehouseDao;
import dao.impl.ExwarehouseDaoImpl;
import domain.ExInfo;
import service.ExWarehouseService;

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
