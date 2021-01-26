package com.inventory.ims.dao.impl;

import com.inventory.ims.dao.SaleDao;
import com.inventory.ims.dto.ItemInTransaction;
import com.inventory.ims.dto.Sale;
import com.inventory.ims.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleDaoImpl implements SaleDao {
    @Override
    public boolean returnSale(int sale, int item, int quant) {
        Connection con = null;
        CallableStatement cstmt = null;
        try {
            con = DatabaseUtil.createConnection();
            cstmt = con.prepareCall("{call INSERT_SALE_RETURN(?,?,?)}");
            cstmt.setInt(1, sale);
            cstmt.setInt(2, item);
            cstmt.setInt(3, quant);
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(cstmt);
            DatabaseUtil.close(con);
        }
        return false;
    }

    @Override
    public List<ItemInTransaction> getItems(int id) {
        List<ItemInTransaction> itemList = new ArrayList<>();
        try (Connection con = DatabaseUtil.createConnection();
             Statement stmt = con.createStatement()) {
            String sql = "SELECT sale_id, i.item_id, sku_quantity, unit_sale_price, item_name\n" +
                    "FROM sale_has_sku s JOIN item i on s.item_id = i.item_id WHERE sale_id = "+ id;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ItemInTransaction i = new ItemInTransaction();
                i.setItemId(rs.getInt(2));
                i.setOrderId(rs.getInt(1));
                i.setQuantity(rs.getInt(3));
                i.setUnitCost(rs.getDouble(4));
                i.setName(rs.getString(5));
                itemList.add(i);
            }
            return itemList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemList;
    }

    @Override
    public List<Sale> getSalesBy(String date, String customer, String store) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT sale_id, cus_name, store_id, order_date\n" +
                "FROM sale LEFT JOIN customer c on sale.cus_id = c.cus_id ");
        ArrayList<String> l = new ArrayList<>();
        if (date != null) {
            l.add(" sale_date = \'"+ date + "\' ");
        }
        if (customer != null) {
            l.add(" cus_name = \'" + customer + "\' ");
        }

        if (store != null) {
            try {
                int i = Integer.parseInt(store);
                l.add(" store_id = " + store + " ");
            } catch (NumberFormatException e) {
                return new ArrayList<>();
            }
        }
        String[] arg = l.toArray(new String[0]);
        if (arg.length > 0) {
            sb.append("WHERE").append(String.join("AND", arg));
        }
        sb.append(";");
        return this.getSales(sb.toString());
    }

    @Override
    public Sale getById(int i) {
        String sql = "SELECT * FROM sale WHERE sale_id= "+ i + ";";
        List<Sale> l = this.getSales(sql);
        if (l.isEmpty()) {
            return null;
        } else {
            return l.get(0);
        }
    }

    @Override
    public List<Sale> getAllSales() {
        String sql = "SELECT sale_id, cus_name, store_id, sale_date\n" +
                "FROM sale JOIN customer c on sale.cus_id = c.cus_id ";
        return this.getSales(sql);
    }

    @Override
    public boolean insert(Sale sale, ItemInTransaction[] items) {
        return false;
    }

    private List<Sale> getSales(String sql) {
        List<Sale> list = new ArrayList<>();
        try (Connection con = DatabaseUtil.createConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Sale sale = new Sale();
                sale.setId(rs.getInt(1));
                sale.setCustomer(rs.getString(2));
                sale.setStore(rs.getInt(3));
                sale.setSale_date(rs.getDate(4));
                list.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
