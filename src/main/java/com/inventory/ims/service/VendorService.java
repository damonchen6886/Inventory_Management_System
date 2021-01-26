package com.inventory.ims.service;

import com.inventory.ims.dto.Item;
import com.inventory.ims.dto.SoldItem;
import com.inventory.ims.dto.Vendor;

import java.util.List;

public interface VendorService {

    boolean insert(Vendor vendor);


    List<Vendor> getByState(String state);

    Vendor getOne(String key);

    List<Vendor> getAll();

    List<Item> getSoldItem(String key);

    boolean addSoldItem(SoldItem s);
}
