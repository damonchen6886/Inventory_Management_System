package com.inventory.ims.service.useCase;

import java.sql.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<InventoryAge.inventoryAgeObject> inventoryAgeObjectList =  new InventoryAge().getAllAges();
        List<ReorderPoint.reorderObject> reorderObjectList = new ReorderPoint().getAllReminders();



        List<Turnover.turnoverObject> turnoverObjectList = new Turnover().getAllTurnovers(4);

        List<ProfitAnalysis.profitAnalysisObject> profitAnalysisObjectList =
                new ProfitAnalysis().getAllProfitRatio(new Date(119,0,1),
                        new Date(119,11,31));

        printList(turnoverObjectList);
    }



    public static <E> void printList(List<E> list) {
        for (E entry : list) {
            System.out.println(entry);
        }
    }
}
