package com.inventory.ims.service.impl;

import com.inventory.ims.dao.OrderDao;
import com.inventory.ims.dao.impl.OrderDaoImpl;
import com.inventory.ims.dto.ItemInTransaction;
import com.inventory.ims.dto.Order;
import com.inventory.ims.service.OrderService;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OrderDao dao = new OrderDaoImpl();

    @Override
    public boolean insert(Order order, ItemInTransaction[] items) {
        return this.dao.insert(order, items);
    }

    @Override
    public Order getOne(String id) {
        try {
            int i = Integer.parseInt(id);
            return this.dao.getById(i);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public List<Order> getAll() {
        return this.dao.getAllOrder();
    }

    @Override
    public List<ItemInTransaction> getItems(String key) {
        try {
            int id = Integer.parseInt(key);
            return this.dao.getItems(id);
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }

    }

    @Override
    public List<Order> getOrders(String date, String vendor, String store) {
        return this.dao.getOrdersBy(date, vendor, store);
    }

    @Override
    public boolean updateDeliverDate(String key) {
        try {
            int id = Integer.parseInt(key);
            return this.dao.updateDeliverDate(id);
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
