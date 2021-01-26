package com.inventory.ims.dto;

import java.util.Arrays;

public class SoldItem {
    private Item[] items;
    private Vendor vendor;

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    @Override
    public String toString() {
        return "SoldItem{" +
                "items=" + Arrays.toString(items) +
                ", vendor=" + vendor +
                '}';
    }
}
