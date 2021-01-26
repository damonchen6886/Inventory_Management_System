package com.inventory.ims.service.impl;

import com.inventory.ims.dao.CategoryDao;
import com.inventory.ims.dao.impl.CategoryDaoImpl;
import com.inventory.ims.dto.Category;
import com.inventory.ims.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao dao = new CategoryDaoImpl();

    @Override
    public boolean insert(Category cat) {
        return this.dao.insert(cat);
    }

    @Override
    public List<Category> getAll() {
        return this.dao.getAllCat();
    }

    @Override
    public Category getOne(String keyword) {
        try {
            int id = Integer.parseInt(keyword);
            return this.dao.getCatById(id);
        } catch (NumberFormatException e) {
            return this.dao.getCatByName(keyword);
        }
    }


}
