package BL;

import java.time.LocalDateTime;

public class QueryRecord {
    private final int workplaceID;
    private final String block;
    private final String itemNumber;
    private final String itemName;
    private final float productionTime;
    private final LocalDateTime productionDate;
    private final String orderNumber;
    private final int orderPos;

    public QueryRecord(int workplaceID, String block, String itemNumber, String itemName, float productionTime, LocalDateTime productionDate, String orderNumber, int orderPos) {
        this.workplaceID = workplaceID;
        this.block = block;
        this.itemNumber = itemNumber;
        this.itemName = itemName;
        this.productionTime = productionTime;
        this.productionDate = productionDate;
        this.orderNumber = orderNumber;
        this.orderPos = orderPos;
    }

    public int getWorkplaceID() {
        return workplaceID;
    }

    public String getBlock() {
        return block;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public float getProductionTime() {
        return productionTime;
    }

    public LocalDateTime getProductionDate() {
        return productionDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public int getOrderPos() {
        return orderPos;
    }
}
