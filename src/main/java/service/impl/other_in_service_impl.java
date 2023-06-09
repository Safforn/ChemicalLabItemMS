package service.impl;

import dao.impl.other_in_dao_impl;
import dao.other_in_dao;
import domain.Other_in_Warehouse;
import service.other_in_service;
import util.UuidUtil;

import java.util.List;
import java.util.UUID;

public class other_in_service_impl implements other_in_service {
    private other_in_dao otherInDao = new other_in_dao_impl();
    @Override
    public List<Other_in_Warehouse> findAll() {
        return otherInDao.search();
    }

    @Override
    public boolean add(Other_in_Warehouse other_in_warehouse) {
        other_in_warehouse.setOther_in_warehouse_id(UuidUtil.getOW());
        other_in_warehouse.setDate(UuidUtil.getCurrentTime());
        return otherInDao.add(other_in_warehouse);
    }
}
