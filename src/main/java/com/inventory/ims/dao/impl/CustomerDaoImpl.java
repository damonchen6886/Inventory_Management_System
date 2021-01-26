package com.inventory.ims.dao.impl;

import com.inventory.ims.dao.CustomerDao;
import com.inventory.ims.dto.Customer;
import com.inventory.ims.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean insert(Customer customer) {
        String sql = "INSERT INTO item_category (cus_name) VALUES ('"
                 + customer.getName() + "')";
        return DatabaseUtil.insertOneRecord(sql) != -1;
    }

    @Override
    public Customer getCusById(int id) {
        String sql = "SELECT * FROM customer WHERE cus_id = " + id + ";";
        return getCus(sql);
    }

    @Override
    public Customer getCusByName(String key) {
        String sql = "SELECT * FROM customer WHERE cus_name = \'" + key + "\';";
        return getCus(sql);
    }

    @Override
    public List<Customer> getAllCus() {
        List<Customer> list = new ArrayList<>();
        try (Connection con = DatabaseUtil.createConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM customer")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt(1));
                customer.setName(rs.getString(2));
                list.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    private Customer getCus(String sql) {
        try (Connection con = DatabaseUtil.createConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt(1));
                customer.setName(rs.getString(2));
                return customer;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
