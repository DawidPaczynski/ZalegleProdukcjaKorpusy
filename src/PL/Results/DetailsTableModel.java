package PL.Results;

import BL.QueryRecord;
import BL.Workplace;
import BL.WorkplaceGroup;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class DetailsTableModel extends AbstractTableModel {

    private final List<DetailsTableProductionOrder> productionOrders;
    private final String[] columnNames = {"Numer", "Nazwa", "Kolor","Ilość" };
    private final Class<?>[] columnClasses = {String.class, String.class, String.class, Integer.class};

    public DetailsTableModel(WorkplaceGroup workplaceGroup, String block) {
        List<QueryRecord> queryRecords=new ArrayList<>();
        for (Workplace workplace : workplaceGroup.getWorkplaces()) {
            try {
                List<QueryRecord> records = workplace.getProductionOrders().get(block);
                queryRecords.addAll(records);
            }catch (NullPointerException e) {
                continue;
            }

        }
        productionOrders=new ArrayList<>();
        for (QueryRecord queryRecord : queryRecords) {
            DetailsTableProductionOrder productionOrder = new DetailsTableProductionOrder(queryRecord);
            if (productionOrders.contains(productionOrder)) {
                int index = productionOrders.indexOf(productionOrder);
                productionOrders.get(index).setQuantity(productionOrders.get(index).getQuantity() + 1);
            } else {
                productionOrders.add(productionOrder);
            }
        }
        productionOrders.sort(Comparator.comparing(DetailsTableProductionOrder::getColor).thenComparing(DetailsTableProductionOrder::getItemNumber));
    }
    @Override
    public int getRowCount() {
        return productionOrders.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return super.getColumnClass(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DetailsTableProductionOrder productionOrder = productionOrders.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return productionOrder.getItemNumber();
            case 1:
                return productionOrder.getItemName();
            case 2:
                return productionOrder.getColor();
            case 3:
                return productionOrder.getQuantity();
            default:
                return null;
        }
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0;
    }
}
