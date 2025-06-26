package PL.WorkplaceGoups;

import BL.ImportFunctions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class WorkplaceGroupsPanel extends JPanel implements ActionListener {
    JTable table;
    JButton removeButton;
    JButton addButton;

    public WorkplaceGroupsPanel() {
        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(topPanel, BorderLayout.NORTH);

        int buttonWidth = 20;
        int buttonHeight = 20;

        ImageIcon addIcon = new ImageIcon(getClass().getResource("/PL/icons/addIcon.png"));
        Image scaledAddImage = addIcon.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
        addButton = new JButton(new ImageIcon(scaledAddImage));
        addButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        addButton.setToolTipText("Dodaj Kapstelle");
        addButton.addActionListener(this);
        topPanel.add(addButton);

        ImageIcon removeIcon = new ImageIcon(getClass().getResource("/PL/icons/removeIcon.png"));
        Image scaledRemoveImage = removeIcon.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
        removeButton = new JButton(new ImageIcon(scaledRemoveImage));
        removeButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        removeButton.setToolTipText("Usuń Kapstelle");
        removeButton.addActionListener(this);
        topPanel.add(removeButton);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(700, 600));
        this.add(scrollPane, BorderLayout.CENTER);

        List<WorkplaceXMLRecord> workplaces = ImportFunctions.importWorkplacesXMLFromFile(ImportFunctions.WORKPLACE_FILE_PATH);
        GroupsTableModel tableModel = new GroupsTableModel(workplaces);

        table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(table);



        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if (source.equals(addButton)) {
            // Handle add button action
            GroupsTableModel tableModel = (GroupsTableModel) table.getModel();
            tableModel.addNewWorkplace(0, "", "", false);
            table.repaint();
            table.revalidate();
        } else if (source.equals(removeButton)) {
            // Handle remove button action
            int selectedRow = table.getSelectedRow();
            if (selectedRow > 0) {
                GroupsTableModel tableModel = (GroupsTableModel) table.getModel();
                int answer= JOptionPane.showConfirmDialog(this, "Czy na pewno chcesz usunąć Kapstelle?", "Usuwanie Kapstelle", JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.YES_OPTION) {
                    tableModel.removeWorkplace(selectedRow);
                    table.repaint();
                    table.revalidate();
                }
            }

        }
    }
}