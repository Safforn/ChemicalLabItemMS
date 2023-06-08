package dao;

import domain.Item;

public interface item_dao {
    public Item findByObjectId(String object_id);
}
