package com.inventory.ims.service.impl;

import com.inventory.ims.dao.ItemDao;
import com.inventory.ims.dao.impl.ItemDaoImpl;
import com.inventory.ims.dto.Item;
import com.inventory.ims.service.ItemService;

import java.util.List;

public class ItemServiceImpl implements ItemService {

    private ItemDao dao = new ItemDaoImpl();

    @Override
    public boolean insert(Item i) {
        return this.dao.insert(i);
    }

    @Override
    public List<Item> getAll() {
        return this.dao.getAllItem();
    }

    @Override
    public Item getOne(String key) {
        try {
            int id = Integer.parseInt(key);
            return this.dao.getItemByID(id);
        } catch (NumberFormatException e) {
            return this.dao.getItemByName(key);
        }
    }

    @Override
    public List<Item> getByCat(String key) {
        return this.dao.getByCat(key);
    }
}
