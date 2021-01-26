package com.inventory.ims.dto;

public class ItemInTransaction {

    private int orderId;
    private int itemId;
    private String name;
    private int quantity;
    private double unitCost;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {

        this.quantity = quantity;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    @Override
    public String toString() {
        return "ItemInTransaction{" +
                "order_id=" + orderId +
                ", item_id=" + itemId +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unit_cost=" + unitCost +
                '}';
    }
}
