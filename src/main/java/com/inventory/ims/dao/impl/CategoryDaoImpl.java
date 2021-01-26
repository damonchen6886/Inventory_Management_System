package com.inventory.ims.dao.impl;

import com.inventory.ims.dao.CategoryDao;
import com.inventory.ims.dto.Category;
import com.inventory.ims.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    @Override
    public boolean insert(Category cat) {
        String sql = "INSERT INTO item_category (cat_name, cat_description) VALUES ('"
                + cat.getName() + "', '" + cat.getDescription() + "')";
        return DatabaseUtil.insertOneRecord(sql) != -1;
    }



    @Override
    public Category getCatById(int catId) {
        String sql = String.format("SELECT * FROM item_category WHERE cat_id = %d", catId);
        return getCat(sql);
    }

    @Override
    public Category getCatByName(String name) {
        String sql = String.format("SELECT * FROM item_category WHERE cat_name = \'%s\';", name);
        return getCat(sql);
    }

    @Override
    public List<Category> getAllCat() {
        List<Category> list = new ArrayList<>();
        try (Connection con = DatabaseUtil.createConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM item_category")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt(1));
                category.setName(rs.getString(2));
                category.setDescription(rs.getString(3));
                list.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Category getCat(String sql) {
        try (Connection con = DatabaseUtil.createConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt(1));
                category.setName(rs.getString(2));
                category.setDescription(rs.getString(3));
                return category;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
