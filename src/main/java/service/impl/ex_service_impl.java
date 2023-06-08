package service.impl;

import dao.ex_dao;
import dao.impl.ex_dao_impl;
import domain.Ex_Warehouse;
import service.ex_service;
import util.UuidUtil;

import java.util.List;

public class ex_service_impl implements ex_service {
    private ex_dao exDao = new ex_dao_impl();
    @Override
    public boolean add(Ex_Warehouse ex_warehouse) {
        ex_warehouse.setEx_warehouse_id(UuidUtil.getUuid());
        ex_warehouse.setDate(UuidUtil.getCurrentTime());
        return exDao.add(ex_warehouse);
    }

    @Override
    public List<Ex_Warehouse> search() {
        return exDao.search();
    }
}
