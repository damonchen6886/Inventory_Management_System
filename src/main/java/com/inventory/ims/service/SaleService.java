package com.inventory.ims.service;

import com.inventory.ims.dto.ItemInTransaction;
import com.inventory.ims.dto.Sale;

import java.util.List;

public interface SaleService {

    boolean insert(Sale sale, ItemInTransaction[] items);

    List<Sale> getAll();

    Sale getOne(String id);

    List<Sale> getSales(String date, String customer, String store);

    List<ItemInTransaction> getItems(String key);

    boolean returnSale(String saleId, String item, String quant);
}
