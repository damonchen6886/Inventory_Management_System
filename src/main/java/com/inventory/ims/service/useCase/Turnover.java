package com.inventory.ims.service.useCase;


import com.inventory.ims.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Turnover {


    public class turnoverObject {
        private int itemID;
        private String  itemName;
        private int storeID;
        private double turnover;

        turnoverObject (int item_id, String itemName, int storeID, double turnover) {
            this.itemID = item_id;
            this.itemName = itemName;
            this.storeID = storeID;
            this.turnover = turnover;
        }

        public int getItemID() {
            return this.itemID;
        }

        public String getItemName() {
            return itemName;
        }

        public int getStoreID() {
            return this. storeID;
        }

        double getTurnover() {
            return this.turnover;
        }

        @Override
        public String toString() {
            return "itemID: " + this.itemID +
                    " itemName: " + this.itemName +
                    " storeID: " + this.storeID +
                    " turnover: " + this.turnover;
        }

    }


    List<turnoverObject> getTurnoverByItemId(int itemId, int numPastWeek) {
        String sql = "CALL get_itr_by_item_for_past_num_week(null, " + itemId +"," +
                      numPastWeek + ");";
        return filterTurnovers(sql);
    }

    List<turnoverObject> getTurnoverByStoreId(int storeId, int numPastWeek) {
        String sql = "CALL get_itr_by_item_for_past_num_week(" + storeId +
                     ", null, " + numPastWeek + ");";
        return filterTurnovers(sql);
    }

    List<turnoverObject> getTurnoverByItemIdAndStoreId(int itemId, int storeId, int numPastWeek) {
        String sql = "CALL get_itr_by_item_for_past_num_week(" + storeId +
                "," + itemId +", " + numPastWeek + ");";
        return filterTurnovers(sql);
    }

    List<turnoverObject> getAllTurnovers(int numPastWeek) {
        String sql =  "CALL get_itr_by_item_for_past_num_week(null, null, " + numPastWeek + ");";
        return filterTurnovers(sql);
    }

    List<turnoverObject> filterTurnovers(String sql) {
        List<turnoverObject> list = new ArrayList<>();
        try (Connection con = DatabaseUtil.createConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                turnoverObject object = new turnoverObject(rs.getInt(3),
                        rs.getString(4),
                        rs.getInt(1),
                        rs.getDouble(9));
                list.add(object);
            }
            list.sort(Comparator.comparing(turnoverObject::getTurnover).reversed());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }
}
