package com.inventory.ims.service;

import com.inventory.ims.dto.RetailStore;

import java.util.List;

public interface RetailStoreService {
    boolean insert(RetailStore store);

    List<RetailStore> getAll();

    RetailStore getOne(String key);

    List<RetailStore> getByState(String state);
}
