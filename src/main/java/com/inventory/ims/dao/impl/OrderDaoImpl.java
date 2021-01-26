package com.inventory.ims.dao.impl;

import com.inventory.ims.dao.OrderDao;
import com.inventory.ims.dto.ItemInTransaction;
import com.inventory.ims.dto.Order;
import com.inventory.ims.util.DatabaseUtil;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public List<Order> getOrdersBy(String date, String v, String s) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT order_id, ven_name, store_id, order_date, delivery_date\n" +
                "FROM supply_order JOIN vendor v on supply_order.ven_id = v.ven_id ");
        ArrayList<String> l = new ArrayList<>();
        if (date != null) {
            l.add(" order_date = \'"+ date + "\' ");
        }
        if (v != null) {
            l.add(" ven_name = \'" + v + "\' ");
        }

        if (s != null) {
            try {
                int i = Integer.parseInt(s);
                l.add(" store_id = " + s + " ");
            } catch (NumberFormatException e) {
                return new ArrayList<>();
            }
        }
        String[] arg = l.toArray(new String[0]);
        if (arg.length > 0) {
            sb.append("WHERE").append(String.join("AND", arg));
        }
        sb.append(";");
        return this.getOrders(sb.toString());
    }

    @Override
    public List<ItemInTransaction> getItems(int id) {
        List<ItemInTransaction> itemList = new ArrayList<>();
        try (Connection con = DatabaseUtil.createConnection();
             Statement stmt = con.createStatement()) {
            String sql = "SELECT order_id, i.item_id, order_quantity, unit_cost, item_name\n" +
                    "FROM sku JOIN item i on sku.item_id = i.item_id WHERE order_id =" + id;
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
    public List<Order> getAllOrder() {
        String sql = "SELECT order_id, ven_name, store_id, order_date, delivery_date\n" +
                "FROM supply_order JOIN vendor v on supply_order.ven_id = v.ven_id ";
        return this.getOrders(sql);
    }

    @Override
    public Order getById(int id) {
        String sql = "SELECT * FROM supply_order WHERE ven_id = "+ id + ";";
        List<Order> l = this.getOrders(sql);
        if (l.isEmpty()) {
            return null;
        } else {
            return l.get(0);
        }
    }

    @Override
    public boolean insert(Order order, ItemInTransaction[] items) {
        int key = -1;
        Connection con = null;
        CallableStatement stmt = null;
        CallableStatement cstmt = null;

        try {
            con = DatabaseUtil.createConnection();
            stmt = con.prepareCall("{call INSERT_INTO_SUPPLY_ORDER(?, ?, ?)}");
            con.setAutoCommit(false);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            stmt.setString(1, order.getVendor());
            stmt.setInt(2, order.getStore());
            stmt.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                key = rs.getInt(1);
            }

            cstmt = con.prepareCall("{call INSERT_INTO_SKU(?,?,?,?)}");
            for (ItemInTransaction i : items) {
                int itemId = i.getItemId();
                double unitCost = i.getUnitCost();
                int quantity = i.getQuantity();
                cstmt.setInt(1, key);
                cstmt.setInt(2, itemId);
                cstmt.setInt(3, quantity);
                cstmt.setDouble(4, unitCost);
                cstmt.execute();
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            DatabaseUtil.rollback(con);
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(stmt);
            DatabaseUtil.close(cstmt);
            DatabaseUtil.close(con);
        }
        return false;
    }

    @Override
    public boolean updateDeliverDate(int id) {
        try (Connection con = DatabaseUtil.createConnection();
             CallableStatement cstmt = con.prepareCall("{call update_order_for_delivery(?)}")) {
            cstmt.setInt(1, id);
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<Order> getOrders(String sql) {
        List<Order> list = new ArrayList<>();
        try (Connection con = DatabaseUtil.createConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt(1));
                order.setVendor(rs.getString(2));
                order.setStore(rs.getInt(3));
                order.setOrderDate(rs.getDate(4));
                order.setDeliveryDate(rs.getDate(5));
                list.add(order);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
