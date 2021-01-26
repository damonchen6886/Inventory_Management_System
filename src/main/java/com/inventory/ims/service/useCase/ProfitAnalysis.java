package com.inventory.ims.service.useCase;

import com.inventory.ims.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class ProfitAnalysis {


     private HashMap<Integer, profitAnalysisObject> map = new HashMap<>();

    public class profitAnalysisObject {
        private int itemID;
        private String itemName;
        private int storeID;
        private double avgInventory = 0;
        private double profit = 0;
        private double ratio;

        profitAnalysisObject(int itemID, int storeID) {
            this.itemID = itemID;
            this.storeID = storeID;

        }

        void setAvgInventory(double avgInventory) {
            this.avgInventory = avgInventory;
        }

        void increaseProfit(double profit) {
            this.profit = this.profit + profit;
        }

        void setItemName(String name) {
            this.itemName = name;
        }

        public int getItemID() {
            return itemID;
        }

        public String getItemName() {
            return itemName;
        }

        public int getStoreID() {
            return storeID;
        }

        public double getAvgInventory() {
            return avgInventory;
        }

        double getProfit() {
            return this.profit;
        }

        void computeRatio() {
            this.ratio = (double) Math.round(this.profit / this.avgInventory * 100) / 100;

        }

        public double getRatio() {
            return this.ratio;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.itemID, this.storeID);
        }

        @Override
        public String toString() {
            return "itemID: " + this.itemID +
                    "       itemName " + this.itemName +
                    "       storeID " + this.storeID +
                    "       avgInventory: " +  String.format("%.2f", this.avgInventory) +
                    "       profit: " + String.format("%.2f", this.profit) +
                    "       ratio: " + String.format("%.2f", this.ratio);
        }

    }

    private void getAvgInventoryByItemID(int itemID, String startDate, String endDate) {
        String sql = "CALL get_avg_hist_inv_by_item(null," + itemID + ", " + startDate +
                ", " + endDate + ");";
        filterAvgInventory(sql);
    }

    private void getAvgInventoryByStoreID(int storeID, String startDate, String endDate) {
        String sql = "CALL get_avg_hist_inv_by_item("+ storeID +", null," + startDate +
                     ", " + endDate +");";
        filterAvgInventory(sql);
    }

    private void getAvgInventoryByItemIDAndStoreID(int itemID, int storeID, String startDate, String endDate) {
        String sql = "CALL get_avg_hist_inv_by_item(" + storeID + ", "+ itemID
                + ", " + startDate + ", " + endDate + ");";
        filterAvgInventory(sql);
    }

    private void getAllAvgInventory(String startDate, String endDate) {
        String sql =  "CALL get_avg_hist_inv_by_item(null, null," + startDate + ", " + endDate + ");";
        filterAvgInventory(sql);
    }


    void filterAvgInventory(String sql) {
        try (Connection con = DatabaseUtil.createConnection();

             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (rs.getDouble(4) != 0) {
                profitAnalysisObject object = new profitAnalysisObject(rs.getInt(2),
                                              rs.getInt(1));
                object.setAvgInventory(rs.getDouble(4));
                this.map.put(object.hashCode(), object);

            }}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getProfitByItemID(int itemID, String startDate, String endDate) {
        String sql = "CALL get_total_profit_by_item(null," + itemID + ", " + startDate +
                     ", " + endDate + ");";
        filterProfit(sql);
    }

    private void getProfitByStoreID(int storeID, String startDate, String endDate) {
        String sql = "CALL get_total_profit_by_item("+ storeID +", null," + startDate +
                ", " + endDate +");";
        filterProfit(sql);
    }

    private void getProfitByItemIDAndStoreID(int itemID, int storeID, String startDate, String endDate) {
        String sql = "CALL get_total_profit_by_item(" + storeID + ", "+ itemID
                      + ", " + startDate + ", " + endDate + ");";
        filterProfit(sql);
    }

    private void getAllProfit(String startDate, String endDate) {
        String sql =  "CALL get_total_profit_by_item(null, null," + startDate + ", " + endDate + ");";
        filterProfit(sql);
    }

    void filterProfit(String sql) {
        try (Connection con = DatabaseUtil.createConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                profitAnalysisObject newObject = new profitAnalysisObject(rs.getInt(3),
                        rs.getInt(1));
                profitAnalysisObject object = this.map.get(newObject.hashCode());
                if (object != null) {
                    object.increaseProfit(rs.getDouble(5));
                    object.setItemName(rs.getString(4));
                    object.computeRatio();

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    List<profitAnalysisObject> getProfitRatioByItemId(int itemID, Date startDate, Date endDate) {
        String start;
        String end;
        if (startDate == null || endDate == null) {
            start = null;
            end = null;
        }
        else {
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            start = df.format(startDate);
            end = df.format(endDate);
        }

        getAvgInventoryByItemID(itemID, start, end);
        getProfitByItemID(itemID, start, end);
        return mapToList(this.map);
    }

    List<profitAnalysisObject> getProfitRatioByStoreId(int storeID, Date startDate, Date endDate) {
        String start;
        String end;
        if (startDate == null || endDate == null) {
            start = null;
            end = null;
        }
        else {
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            start = df.format(startDate);
            end = df.format(endDate);
        }

        getAvgInventoryByStoreID(storeID, start, end);
        getProfitByStoreID(storeID, start, end);
        return mapToList(this.map);

    }

    profitAnalysisObject getProfitRatioByItemIdAndStoreId(int storeID, int itemID, Date startDate, Date endDate) {
        String start;
        String end;
        if (startDate == null || endDate == null) {
            start = null;
            end = null;
        }
        else {
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            start = df.format(startDate);
            end = df.format(endDate);
        }

        getAvgInventoryByItemIDAndStoreID(itemID, storeID, start, end);
        getProfitByItemIDAndStoreID(itemID, storeID, start, end);
        return mapToList(this.map).get(0);

    }


    List<profitAnalysisObject> getAllProfitRatio(Date startDate, Date endDate) {
        String start;
        String end;
        if (startDate == null || endDate == null) {
            start = null;
            end = null;
        }
        else {
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            start = df.format(startDate);
            end = df.format(endDate);
        }

        getAllAvgInventory(start, end);
        getAllProfit(start, end);
        return mapToList(this.map);

    }

    private List<profitAnalysisObject> mapToList(HashMap<Integer, profitAnalysisObject> map) {
        List<profitAnalysisObject> list = new ArrayList<>();
        for (Map.Entry<Integer, ProfitAnalysis.profitAnalysisObject> entry : map.entrySet()) {
            list.add(entry.getValue());
        }
        list.sort(Comparator.comparing(profitAnalysisObject::getRatio).reversed());

        return list;

    }
    }

