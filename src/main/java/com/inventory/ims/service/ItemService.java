package com.inventory.ims.service;

import com.inventory.ims.dto.Item;

import java.util.List;

public interface ItemService {

    boolean insert(Item i);

    List<Item> getAll();

    Item getOne(String key);

    List<Item> getByCat(String key);
}