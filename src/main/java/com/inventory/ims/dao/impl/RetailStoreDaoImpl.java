package com.inventory.ims.dao.impl;

import com.inventory.ims.dao.RetailStoreDao;
import com.inventory.ims.dto.RetailStore;
import com.inventory.ims.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RetailStoreDaoImpl implements RetailStoreDao {

    @Override
    public boolean insert(RetailStore store) {
        String sql = "INSERT INTO retail_store (store_address, store_state, store_zip) VALUES (\'"
                + store.getAddress() + "\', \'"
                + store.getState() + "\', \'"
                + store.getZipCode() + "\');";
        return DatabaseUtil.insertOneRecord(sql) != -1;
    }

    @Override
    public RetailStore getById(int id) {
        String sql = "SELECT * FROM retail_store WHERE store_id = "+ id + ";";
        List<RetailStore> l = this.getRetailStores(sql);
        if (l.isEmpty()) {
            return null;
        } else {
            return l.get(0);
        }
    }

    @Override
    public List<RetailStore> getByState(String state) {
        String sql = "SELECT * FROM retail_store WHERE store_state = \'"+ state + "\';";
        return this.getRetailStores(sql);
    }

    @Override
    public List<RetailStore> getAll() {
        String sql = "SELECT * FROM retail_store;";
        return this.getRetailStores(sql);
    }



    private List<RetailStore> getRetailStores(String sql) {
        List<RetailStore> list = new ArrayList<>();
        try (Connection con = DatabaseUtil.createConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RetailStore store = new RetailStore();
                store.setId(rs.getInt(1));
                store.setAddress(rs.getString(2));
                store.setState(rs.getString(3));
                store.setZipCode(rs.getString(4));
                list.add(store);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
