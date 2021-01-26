package com.inventory.ims.service.useCase;

import com.inventory.ims.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class InventoryAge {


    public class inventoryAgeObject {
        private int itemID;
        private int SKUID;
        private String name;
        private int age;


        inventoryAgeObject(int itemID, int SKUID, String name, int age) {
            this.itemID = itemID;
            this.SKUID = SKUID;
            this.name = name;
            this.age = age;

        }

        public int getItemID() {
            return this.itemID;
        }

        public int getSKUID() {
            return this.SKUID;
        }

        public String getName() {
            return this.name;
        }

        public int getAge() {
            return this.age;
        }

        @Override
        public String toString() {
            return "itemID: " + this.itemID +
                    " SKUID: " + this.SKUID +
                    " name: " + this.name +
                    " age: " + this.age;
        }
    }


    List<inventoryAgeObject> getAgeByItemId(int itemId) {
        String sql = "CALL get_remaining_inventory_by_age_of_inv(null, " + itemId + ");";
        return filterAges(sql);
    }

    List<inventoryAgeObject> getAgeByStoreId(int storeId) {
        String sql = "CALL get_remaining_inventory_by_age_of_inv(" + storeId + ", null);";
        return filterAges(sql);
    }

    List<inventoryAgeObject> getAgeByItemIdAndStoreId(int itemId, int storeId) {
        String sql = "CALL get_remaining_inventory_by_age_of_inv(" + storeId + ", " +
                itemId + ");";
        return filterAges(sql);
    }

    List<inventoryAgeObject> getAllAges() {
        String sql = "CALL get_remaining_inventory_by_age_of_inv(null, null);";
        return filterAges(sql);
    }

    List<inventoryAgeObject> filterAges(String sql) {
        List<inventoryAgeObject> list = new ArrayList<>();
        try (Connection con = DatabaseUtil.createConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                inventoryAgeObject object = new inventoryAgeObject(rs.getInt(4),
                        rs.getInt(1), rs.getString(5),
                        rs.getInt(9));
                list.add(object);
            }
            list.sort(Comparator.comparing(inventoryAgeObject::getAge).reversed());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;

    }



}