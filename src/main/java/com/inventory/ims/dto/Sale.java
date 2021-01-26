package com.inventory.ims.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Sale {

    private int id;
    private int store;
    private String customer;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date sale_date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getSale_date() {
        return sale_date;
    }

    public void setSale_date(Date sale_date) {
        this.sale_date = sale_date;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", store=" + store +
                ", customer=" + customer +
                ", sale_date=" + sale_date +
                '}';
    }
}
