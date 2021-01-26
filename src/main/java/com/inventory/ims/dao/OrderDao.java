package com.inventory.ims.dao;

import com.inventory.ims.dto.*;

import java.util.List;

public interface OrderDao {
    List<Order> getOrdersBy(String date, String v, String s);

    List<ItemInTransaction> getItems(int id);

    List<Order> getAllOrder();

    Order getById(int id);

    boolean insert(Order order, ItemInTransaction[] items);

    boolean updateDeliverDate(int key);
}
