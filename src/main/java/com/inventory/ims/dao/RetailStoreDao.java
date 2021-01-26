package com.inventory.ims.dao;

import com.inventory.ims.dto.RetailStore;

import java.util.List;

public interface RetailStoreDao {

    RetailStore getById(int id);

    List<RetailStore> getByState(String state);

    List<RetailStore> getAll();

    boolean insert(RetailStore store);
}
