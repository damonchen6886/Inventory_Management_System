package com.inventory.ims.service;

import com.inventory.ims.dto.ItemInTransaction;
import com.inventory.ims.dto.Order;

import java.util.List;

public interface OrderService {


    boolean insert(Order order, ItemInTransaction[] items);

    Order getOne(String id);

    List<Order> getAll();

    List<ItemInTransaction> getItems(String key);

    List<Order> getOrders(String date, String vendor, String store);

    boolean updateDeliverDate(String key);
}
