package PL.Results;

import BL.ImportFunctions;
import BL.WorkplaceGroup;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ResultPanel extends JPanel {


    public ResultPanel() throws SQLException {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Wyniki");
        label.setAlignmentX(CENTER_ALIGNMENT);
        this.add(label);

        List<WorkplaceGroup> workplaceGroups = ImportFunctions.createGroups();
        JTable table = new JTable(new ResultTableModel(workplaceGroups));
        table.getColumnModel().getColumn(0).setPreferredWidth(300);
        table.setDefaultRenderer(Object.class, new ResultTableCellRender());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(950, 700));
        this.add(scrollPane);


    }


}
