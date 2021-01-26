package com.inventory.ims.dao;

import com.inventory.ims.dto.Category;

import java.util.List;

public interface CategoryDao {

    boolean insert(Category cat);

    Category getCatById(int catId);

    Category getCatByName(String name);

    List<Category> getAllCat();

}
