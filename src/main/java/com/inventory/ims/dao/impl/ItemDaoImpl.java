package com.inventory.ims.dao.impl;

import com.inventory.ims.dao.ItemDao;
import com.inventory.ims.dto.Category;
import com.inventory.ims.dto.Item;
import com.inventory.ims.service.CategoryService;
import com.inventory.ims.service.impl.CategoryServiceImpl;
import com.inventory.ims.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {



    @Override
    public boolean insert(Item i) {
        String sql = "INSERT INTO item (cat_id, item_name, item_unit_price) VALUES (" + i.getCategory().getId() + ", '"
                + i.getName() + "', " + i.getPrice() + ")";
        return DatabaseUtil.insertOneRecord(sql) != -1;
    }

    @Override
    public List<Item> getAllItem() {
       return this.getItems("SELECT * FROM item");
    }

    @Override
    public Item getItemByID(int id) {
        List<Item> l = this.getItems("SELECT * FROM item WHERE item_id = " + id + ";");
        return l.isEmpty() ? null : l.get(0);
    }

    @Override
    public Item getItemByName(String key) {
        List<Item> l = this.getItems("SELECT * FROM item WHERE item_name = \'" + key + "\';");
        return l.isEmpty() ? null : l.get(0);
    }

    @Override
    public List<Item> getByCat(String key) {
        CategoryService catDao = new CategoryServiceImpl();
        Category category = catDao.getOne(key);
        return this.getItems("SELECT * FROM item WHERE cat_id = " + category.getId() + ";");
    }

    private List<Item> getItems(String sql) {
        List<Item> list = new ArrayList<>();
        try (Connection con = DatabaseUtil.createConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt(1));
                item.setCategory(new CategoryDaoImpl().getCatById(rs.getInt(2)));
                item.setName(rs.getString(3));
                item.setPrice(rs.getDouble(4));
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
