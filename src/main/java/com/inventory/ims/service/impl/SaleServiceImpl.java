package com.inventory.ims.service.impl;

import com.inventory.ims.dao.SaleDao;
import com.inventory.ims.dao.impl.SaleDaoImpl;
import com.inventory.ims.dto.ItemInTransaction;
import com.inventory.ims.dto.Sale;
import com.inventory.ims.service.SaleService;

import java.util.ArrayList;
import java.util.List;

public class SaleServiceImpl implements SaleService {

    private SaleDao dao = new SaleDaoImpl();

    @Override
    public boolean insert(Sale sale, ItemInTransaction[] items) {
        return this.dao.insert(sale, items);
    }

    @Override
    public List<Sale> getAll() {
        return this.dao.getAllSales();
    }

    @Override
    public Sale getOne(String id) {
        try {
            int i = Integer.parseInt(id);
            return this.dao.getById(i);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public List<Sale> getSales(String date, String customer, String store) {
        return this.dao.getSalesBy(date, customer, store);
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
    public boolean returnSale(String saleId, String item, String quant) {
        try {
            int a = Integer.parseInt(saleId);
            int b = Integer.parseInt(item);
            int c = Integer.parseInt(quant);
            return this.dao.returnSale(a, b, c);
        } catch (NumberFormatException e) {
            return false;
        }


    }
}
