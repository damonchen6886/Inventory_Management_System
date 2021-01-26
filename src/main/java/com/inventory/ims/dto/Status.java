package com.inventory.ims.dto;

public class Status {

    private int store;
    private String itemName;
    private int stock;

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Status{" +
                "store=" + store +
                ", item=" + itemName +
                ", stock=" + stock +
                '}';
    }
}
