package com.inventory.ims.dao;

import com.inventory.ims.dto.Item;

import java.util.List;

public interface ItemDao {

    boolean insert(Item i);

    List<Item> getAllItem();

    Item getItemByID(int id);

    Item getItemByName(String key);

    List<Item> getByCat(String key);
}
