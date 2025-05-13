package PL.Results;

import BL.WorkplaceGroup;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ResultTableCellRender extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel c = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row == table.getRowCount() - 1) { // Last row
            c.setFont(c.getFont().deriveFont(Font.BOLD));
            return c;
        }
        ResultTableModel model= (ResultTableModel)table.getModel();
        WorkplaceGroup workplaceGroup = model.getWorkplaceGroups().get(row);
        if (column == 0) {
            String groupsWorkplaces = "";
            for (int i = 0; i < workplaceGroup.getWorkplaces().size(); i++) {
                groupsWorkplaces += workplaceGroup.getWorkplaces().get(i).getId()+", ";
            }
            c.setToolTipText(groupsWorkplaces);
        }
        else {
            String block = model.getColumnName(column);
            int cabinetCount = 0;
            for (int i = 0; i < workplaceGroup.getWorkplaces().size(); i++) {
                if (workplaceGroup.getWorkplaces().get(i).getProductionOrders().containsKey(block)) {
                    cabinetCount += workplaceGroup.getWorkplaces().get(i).getProductionOrders().get(block).size();
                }
            }
            c.setToolTipText("Liczba: " + cabinetCount);
        }
        return c;
    }
}