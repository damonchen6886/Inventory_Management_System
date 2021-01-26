package com.inventory.ims.dao;

import com.inventory.ims.dto.Customer;

import java.util.List;

public interface CustomerDao {
    boolean insert(Customer customer);

    Customer getCusById(int id);

    Customer getCusByName(String key);

    List<Customer> getAllCus();
}
