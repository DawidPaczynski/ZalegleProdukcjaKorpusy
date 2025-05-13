package PL.WorkplaceGoups;

import BL.ImportFunctions;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class GroupsTableModel extends AbstractTableModel {
    private final List<WorkplaceXMLRecord> workplaces;

    private final String[] columnNames = {"Kapstelle", "Nazwa", "Grupa"};
    private final Class<?>[] columnClasses = {Integer.class, String.class, String.class};

    public GroupsTableModel(List<WorkplaceXMLRecord> workplaces) {
        this.workplaces = workplaces;
    }
    @Override
    public int getRowCount() {
        return workplaces.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        WorkplaceXMLRecord workplace = workplaces.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return workplace.getId();
            case 1:
                return workplace.getName();
            case 2:
                return workplace.getGroupName();
            default:
                return null;
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        WorkplaceXMLRecord workplace = workplaces.get(rowIndex);
        int oldId = workplace.getId();
        switch (columnIndex) {
            case 0:
                workplace.setId((Integer) aValue);
                break;
            case 1:
                workplace.setName((String) aValue);
                break;
            case 2:
                workplace.setGroupName((String) aValue);
                break;
            default:
                break;
        }
        ImportFunctions.updateWorkplaceInFile(ImportFunctions.WORKPLACE_FILE_PATH, oldId, workplace.getId(), workplace.getName(), workplace.getGroupName());

    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }


    public void addNewWorkplace(int workplaceId, String workplaceName, String GroupName){
        WorkplaceXMLRecord newWorkplace = new WorkplaceXMLRecord(workplaceId, workplaceName, GroupName);
        workplaces.add(newWorkplace);
        //this.addRow(new Object[]{workplaceId, workplaceName, GroupName});
        ImportFunctions.addWorkplaceToFile(ImportFunctions.WORKPLACE_FILE_PATH, workplaceId, workplaceName, GroupName);
    }

    public void removeWorkplace(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < workplaces.size()) {
            WorkplaceXMLRecord workplace = workplaces.get(rowIndex);
            workplaces.remove(workplace);
            //this.removeRow(rowIndex);
            ImportFunctions.removeWorkplaceFromFile(ImportFunctions.WORKPLACE_FILE_PATH, workplace.getId());
        }
    }



}

