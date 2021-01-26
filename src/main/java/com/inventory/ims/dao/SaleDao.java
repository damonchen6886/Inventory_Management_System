package com.inventory.ims.dao;

import com.inventory.ims.dto.ItemInTransaction;
import com.inventory.ims.dto.Sale;

import java.util.List;

public interface SaleDao {
    boolean returnSale(int sale, int item, int quant);

    List<ItemInTransaction> getItems(int id);

    List<Sale> getSalesBy(String date, String customer, String store);

    Sale getById(int i);

    List<Sale> getAllSales();

    boolean insert(Sale sale, ItemInTransaction[] items);
}
