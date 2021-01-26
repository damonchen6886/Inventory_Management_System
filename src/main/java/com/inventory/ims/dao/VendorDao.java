package com.inventory.ims.dao;

import com.inventory.ims.dto.Item;
import com.inventory.ims.dto.Vendor;

import java.util.List;

public interface VendorDao {
    boolean insert(Vendor vendor);


    List<Vendor> getByState(String state);

    Vendor getById(int id);

    Vendor getByName(String key);

    List<Vendor> getAllVendor();

    List<Item> getSoldItems(String name);

    boolean addSoldItem(int id, Item[] items);
}
