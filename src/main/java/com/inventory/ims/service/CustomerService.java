package com.inventory.ims.service;


import com.inventory.ims.dto.Customer;

import java.util.List;

public interface CustomerService {

    boolean insert(Customer customer);

    Customer getOne(String key);

    List<Customer> getAll();
}
