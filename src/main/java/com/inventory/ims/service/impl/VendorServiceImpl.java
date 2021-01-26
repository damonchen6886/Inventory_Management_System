package com.inventory.ims.service.impl;

import com.inventory.ims.dao.VendorDao;
import com.inventory.ims.dao.impl.VendorDaoImpl;
import com.inventory.ims.dto.Item;
import com.inventory.ims.dto.SoldItem;
import com.inventory.ims.dto.Vendor;
import com.inventory.ims.service.VendorService;

import java.util.List;

public class VendorServiceImpl implements VendorService {

    private VendorDao dao = new VendorDaoImpl();

    @Override
    public boolean insert(Vendor vendor) {
        return this.dao.insert(vendor);
    }


    @Override
    public List<Vendor> getByState(String state) {
        return this.dao.getByState(state);
    }

    @Override
    public Vendor getOne(String key) {
        if (key == null) {
            return null;
        }
        try {
            int id = Integer.parseInt(key);
            return this.dao.getById(id);
        } catch (NumberFormatException e) {
            return this.dao.getByName(key);
        }
    }

    @Override
    public List<Vendor> getAll() {
        return this.dao.getAllVendor();
    }

    @Override
    public List<Item> getSoldItem(String key) {
        try {
            int id = Integer.parseInt(key);
            return this.dao.getSoldItems(this.dao.getById(id).getName());
        } catch (NumberFormatException e) {
            return this.dao.getSoldItems(key);
        }
    }

    @Override
    public boolean addSoldItem(SoldItem s) {
        Vendor v = s.getVendor();
        int id = v.getId();
        if (id == 0) {
            id = this.dao.getByName(v.getName()).getId();
        }
        return this.dao.addSoldItem(v.getId(), s.getItems());
    }
}
