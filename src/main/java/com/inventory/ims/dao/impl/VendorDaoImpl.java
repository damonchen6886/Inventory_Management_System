package com.inventory.ims.dao.impl;

import com.inventory.ims.dao.VendorDao;
import com.inventory.ims.dto.Item;
import com.inventory.ims.dto.Vendor;
import com.inventory.ims.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendorDaoImpl implements VendorDao {
    @Override
    public boolean insert(Vendor vendor) {
        String sql = "INSERT INTO vendor (ven_name, ven_address, ven_state, ven_zip, ven_description) VALUES (\'"
                + vendor.getName() + "\', \'"
                + vendor.getAddress() + "\', \'"
                + vendor.getState() + "\', \'"
                + vendor.getZipCode() + "\', \'"
                + vendor.getDescription() + "\');";
        return DatabaseUtil.insertOneRecord(sql) != -1;
    }




    @Override
    public List<Vendor> getByState(String state) {
        String sql = "SELECT * FROM vendor WHERE ven_state = \'"+ state + "\';";
        return this.getVendors(sql);
    }

    @Override
    public Vendor getById(int id) {
        String sql = "SELECT * FROM vendor WHERE ven_id = "+ id + ";";
        List<Vendor> l = this.getVendors(sql);
        if (l.isEmpty()) {
            return null;
        } else {
            return l.get(0);
        }
    }

    @Override
    public Vendor getByName(String key) {
        String sql = "SELECT * FROM vendor WHERE ven_id = \'"+ key + "\';";
        List<Vendor> l = this.getVendors(sql);
        if (l.isEmpty()) {
            return null;
        } else {
            return l.get(0);
        }
    }

    @Override
    public List<Vendor> getAllVendor() {
        String sql = "SELECT * FROM vendor;";
        return this.getVendors(sql);
    }

    @Override
    public List<Item> getSoldItems(String name) {
        List<Item> list = new ArrayList<>();
        try (Connection con = DatabaseUtil.createConnection();
             CallableStatement stmt = con.prepareCall("{CALL GET_ITEMS_FROM_VENDOR(?)}")) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt(1));
                item.setCategory(new CategoryDaoImpl().getCatByName(rs.getString(2)));
                item.setName(rs.getString(3));
                item.setPrice(rs.getDouble(4));
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean addSoldItem(int id, Item[] items) {
        Statement  stmt = null;
        Connection con = null;
        try {
            con = DatabaseUtil.createConnection();
            stmt = con.createStatement();
            con.setAutoCommit(false);
            for (Item i : items) {

                int item_id = i.getId();
                String sql = "INSERT INTO vendor_has_item (ven_id, item_id) VALUES (" + id + ", "
                        + item_id + ")";
                stmt.executeUpdate(sql);

            }
            con.commit();
            return true;
        } catch (SQLException e) {
            DatabaseUtil.rollback(con);
            e.printStackTrace();

        } finally {
            DatabaseUtil.close(stmt);
            DatabaseUtil.close(con);
        }
        return false;
    }

    private List<Vendor> getVendors(String sql) {
        List<Vendor> list = new ArrayList<>();
        try (Connection con = DatabaseUtil.createConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Vendor vendor = new Vendor();
                vendor.setId(rs.getInt(1));
                vendor.setName(rs.getString(2));
                vendor.setAddress(rs.getString(3));
                vendor.setState(rs.getString(4));
                vendor.setZipCode(rs.getString(5));
                vendor.setDescription(rs.getString(6));
                list.add(vendor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
