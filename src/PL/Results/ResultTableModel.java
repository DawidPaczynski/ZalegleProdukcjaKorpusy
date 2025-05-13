package PL.Results;

import BL.QueryRecord;
import BL.Workplace;
import BL.WorkplaceGroup;

import javax.swing.table.AbstractTableModel;
import java.text.DecimalFormat;
import java.util.*;

public class ResultTableModel extends AbstractTableModel {
    private final List<WorkplaceGroup> workplaceGroups;
    private final String[] columnNames;
    private final Class<?>[] columnClasses;
    DecimalFormat df;

    public ResultTableModel(List<WorkplaceGroup> workplaceGroups) {
        this.workplaceGroups = workplaceGroups;
        SortedSet<String> allBlocks = getAllBlocksFromWorkplaceGroups(workplaceGroups);
        this.columnNames = allBlocks.toArray(new String[0]);
        this.columnClasses = new Class<?>[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            columnClasses[i] = String.class;
        }

        df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
    }

    @Override
    public int getRowCount() {
        return workplaceGroups.size()+1;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < workplaceGroups.size()) {
            // Regular rows
            if (columnIndex == 0) {
                return workplaceGroups.get(rowIndex).getName();
            }
            String block = columnNames[columnIndex];
            float totalTime = 0f;
            for (Workplace workplace : workplaceGroups.get(rowIndex).getWorkplaces()) {
                if (workplace.getProductionTimes().containsKey(block)) {
                    totalTime += workplace.getProductionTimes().get(block);
                }
            }
            return df.format(totalTime);
        } else {
            // Last row (sum row)
            if (columnIndex == 0) {
                return "Sum";
            }
            String block = columnNames[columnIndex];
            float totalSum = 0f;
            for (WorkplaceGroup group : workplaceGroups) {
                for (Workplace workplace : group.getWorkplaces()) {
                    if (workplace.getProductionTimes().containsKey(block)) {
                        totalSum += workplace.getProductionTimes().get(block);
                    }
                }
            }
            return df.format(totalSum);
        }
    }

    private SortedSet<String> getAllBlocksFromWorkplaceGroups(List<WorkplaceGroup> workplaceGroups) {
        SortedSet<String> allBlocks = new TreeSet<>((o1, o2) -> {
            if (o1.equals("Grupa")) return -1; // "Grupa" comes first
            if (o2.equals("Grupa")) return 1;  // "Grupa" comes first
            else if (o1.equals(o2)) return 0; // same block
            else {
                // set by Week first
                String week1= o1.split("/")[0].replaceAll("[^0-9]", "");
                String week2= o2.split("/")[0].replaceAll("[^0-9]", "");
                if(!week1.equals(week2)) {
                    return Integer.compare(Integer.parseInt(week1), Integer.parseInt(week2));
                }
                else {
                    // set by Express second
                    if (o1.startsWith("E")){
                        return -1;
                    }
                    else {
                        // set by block third
                        String block1=o1.split("/")[1].replaceAll("[^0-9]", "");
                        String block2=o2.split("/")[1].replaceAll("[^0-9]", "");
                        if (!block1.equals(block2)) {
                            return Integer.compare(Integer.parseInt(block1), Integer.parseInt(block2));
                        }
                        else {
                            //set Colours Block fourth
                            if(o1.split("/")[1].startsWith("K")){
                                return -1;
                            }
                            else {
                                return 1;
                            }
                        }
                    }
                }
            }
        });

        allBlocks.add("Grupa"); // Add "Grupa" explicitly

        for (WorkplaceGroup group : workplaceGroups) {
            for (Workplace workplace : group.getWorkplaces()) {
                TreeMap<String, List<QueryRecord>> productionOrders = workplace.getProductionOrders();
                allBlocks.addAll(productionOrders.keySet());
            }
        }
        return allBlocks;
    }

    public List<WorkplaceGroup> getWorkplaceGroups() {
        return workplaceGroups;
    }
}
