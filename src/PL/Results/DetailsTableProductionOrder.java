package PL.Results;

import BL.QueryRecord;

public class DetailsTableProductionOrder extends QueryRecord {

    private int quantity;

    public DetailsTableProductionOrder(QueryRecord queryRecord) {
        super(queryRecord.getWorkplaceID(), queryRecord.getBlock(), queryRecord.getItemNumber(), queryRecord.getItemName(), queryRecord.getProductionTime(),
                queryRecord.getProductionDate(), queryRecord.getOrderNumber(), queryRecord.getOrderPos(), queryRecord.getColor());
        this.quantity = 1;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object obj) {
        DetailsTableProductionOrder that = (DetailsTableProductionOrder) obj;
        return getItemNumber().equals(that.getItemNumber()) && getColor().equals(that.getColor());
    }

    @Override
    public int hashCode() {
        int result = getItemNumber().hashCode();
        result = 31 * result + getItemName().hashCode()+ getColor().hashCode();
        return result;
    }
}
