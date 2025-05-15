package PL.Results;

import BL.Workplace;
import BL.WorkplaceGroup;

import javax.swing.*;
import java.awt.*;

public class DetailsDialog extends JDialog {

    public DetailsDialog(JFrame parent, WorkplaceGroup workplaceGroup, String block) {
        super(parent, "Szczegóły grupy", true);
        this.setSize(550, 400);
        this.setLocationRelativeTo(parent);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        JScrollPane scrollPane = new JScrollPane();
        JTable table = new JTable(new DetailsTableModel(workplaceGroup, block));
        table.getColumnModel().getColumn(0).setPreferredWidth(180);
        table.getColumnModel().getColumn(1).setPreferredWidth(270);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getColumnModel().getColumn(3).setPreferredWidth(50);
        scrollPane.setViewportView(table);

        this.add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
