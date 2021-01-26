package com.inventory.ims.service.impl;


import com.inventory.ims.dao.CustomerDao;
import com.inventory.ims.dao.impl.CustomerDaoImpl;
import com.inventory.ims.dto.Customer;
import com.inventory.ims.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDao dao = new CustomerDaoImpl();

    @Override
    public boolean insert(Customer customer) {
        return this.dao.insert(customer);
    }

    @Override
    public Customer getOne(String key) {
        try {
            int id = Integer.parseInt(key);
            return this.dao.getCusById(id);
        } catch (NumberFormatException e) {
            return this.dao.getCusByName(key);
        }
    }

    @Override
    public List<Customer> getAll() {
        return this.dao.getAllCus();
    }
}
