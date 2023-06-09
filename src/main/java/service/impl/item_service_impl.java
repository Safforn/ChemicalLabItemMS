package service.impl;

import dao.impl.item_dao_impl;
import dao.impl.object_entry_dao_impl;
import dao.item_dao;
import dao.object_entry_dao;
import domain.Item;
import service.item_service;
import util.UuidUtil;


import java.util.List;

public class item_service_impl implements item_service {
    private item_dao i_dao = new item_dao_impl();
    @Override
    public Item search(String object_id) {
        return i_dao.findByObjectId(object_id);
    }

    @Override
    public boolean add(List<Item> items) {
        for (Item item : items) {
            if (item.getObject_id() == null) item.setObject_id(UuidUtil.getII());
            i_dao.add(item);
        }
        return false;
    }
}
