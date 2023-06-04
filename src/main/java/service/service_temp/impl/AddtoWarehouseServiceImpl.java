package service.service_temp.impl;

import dao.dao_temp.AddtoWarehouseDao;
import dao.dao_temp.impl.AddtoWarehouseDaoImpl;
import domain.AddtoInfo;
import service.service_temp.AddtoWarehouseService;

import java.util.List;

public class AddtoWarehouseServiceImpl implements AddtoWarehouseService {
    AddtoWarehouseDao addtoWarehouseDao = new AddtoWarehouseDaoImpl();

    /**
     *
     * @return
     */
    @Override
    public List<AddtoInfo> searchAllAdd() {
        return addtoWarehouseDao.searchAll();
    }
}
