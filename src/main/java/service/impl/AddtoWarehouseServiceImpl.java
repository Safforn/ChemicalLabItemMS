package service.impl;

import dao.AddtoWarehouseDao;
import dao.impl.AddtoWarehouseDaoImpl;
import domain.AddtoInfo;
import service.AddtoWarehouseService;

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
