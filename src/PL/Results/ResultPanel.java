package PL.Results;

import BL.ImportFunctions;
import BL.WorkplaceGroup;
import DA.Export;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class ResultPanel extends JPanel {
    private JTable table;

    public ResultPanel() throws SQLException {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        ImageIcon exportIcon = new ImageIcon(getClass().getResource("/PL/icons/ExcelIcon.png"));
        Image scaledExportIcon = exportIcon.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH);
        JButton exportButton = new JButton(new ImageIcon(scaledExportIcon));
        exportButton.setPreferredSize(new Dimension(30, 30));
        exportButton.setToolTipText("Eksportuj do pliku");
        exportButton.addActionListener(e -> {
            Export.exportTableToExcel(table, System.getProperty("user.home") + "\\Downloads\\Zestawienie Korpusy.xlsx");
        });
        exportButton.setAlignmentX(RIGHT_ALIGNMENT);
        exportButton.setMaximumSize(new Dimension(30, 30));
        this.add(exportButton);

        JLabel emptyLabel = new JLabel(" ");
        emptyLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        this.add(emptyLabel);

        List<WorkplaceGroup> workplaceGroups = ImportFunctions.createGroups();

        table = new JTable(new ResultTableModel(workplaceGroups));
        table.getColumnModel().getColumn(0).setPreferredWidth(300);
        table.setDefaultRenderer(Object.class, new ResultTableCellRender());
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e, table);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e, table);
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(950, 700));
        this.add(scrollPane);
    }

    private void showPopup(java.awt.event.MouseEvent e,JTable table) {
        if (e.isPopupTrigger()) {
            int row = table.rowAtPoint(e.getPoint());
            int column = table.columnAtPoint(e.getPoint());
            if (row != -1 && column != -1) {
                table.setRowSelectionInterval(row, row);
                table.setColumnSelectionInterval(column, column);
                JPopupMenu popupMenu = createCellPopUp(table, row, column);
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
    private JPopupMenu createCellPopUp(JTable table, int row, int column) {
        JPopupMenu popupMenu = new JPopupMenu();
        ResultTableModel model = (ResultTableModel) table.getModel();
        WorkplaceGroup workplaceGroup = model.getWorkplaceGroups().get(row);
        JMenuItem menuItem = new JMenuItem("Zobacz szczegóły");
        String block=model.getColumnName(column);

        menuItem.addActionListener(e -> {
            new DetailsDialog((JFrame) SwingUtilities.getWindowAncestor(table), workplaceGroup, block);

        });

        popupMenu.add(menuItem);
        popupMenu.show(table, table.getCellRect(row, column, true).x, table.getCellRect(row, column, true).y);
        return popupMenu;
    }



}
