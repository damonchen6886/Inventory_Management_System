package com.inventory.ims.service.impl;

import com.inventory.ims.dao.RetailStoreDao;
import com.inventory.ims.dao.impl.RetailStoreDaoImpl;
import com.inventory.ims.dto.RetailStore;
import com.inventory.ims.service.RetailStoreService;

import java.util.List;

public class RetailStoreServiceImpl implements RetailStoreService {

    private RetailStoreDao dao = new RetailStoreDaoImpl();

    @Override
    public boolean insert(RetailStore store) {
        return this.dao.insert(store);
    }

    @Override
    public List<RetailStore> getAll() {
        return this.dao.getAll();
    }

    @Override
    public RetailStore getOne(String key) {
        try {
            int id = Integer.parseInt(key);
            return this.dao.getById(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<RetailStore> getByState(String state) {
        return this.dao.getByState(state);
    }
}
