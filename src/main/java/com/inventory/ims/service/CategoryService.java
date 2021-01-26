package com.inventory.ims.service;

import com.inventory.ims.dto.Category;

import java.util.List;

public interface CategoryService {

    boolean insert(Category cat);

    List<Category> getAll();

    Category getOne(String keyword);

}
