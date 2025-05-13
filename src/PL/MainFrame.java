package PL;

import PL.Results.ResultPanel;
import PL.WorkplaceGoups.WorkplaceGroupsPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class MainFrame extends JFrame {

    private JPanel mainPanel;

    public MainFrame(){
        this.setTitle("Stan produkcji - Korpusy");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        mainPanel=new JPanel();
        this.add(mainPanel, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu optionsMenu = new JMenu("Opcje");
        menuBar.add(optionsMenu);

        JMenuItem loadMenuItem = new JMenuItem("Pobierz dane");
        loadMenuItem.addActionListener(e -> {
            mainPanel.removeAll();
            try {
                mainPanel.add(new ResultPanel(), BorderLayout.CENTER);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        optionsMenu.add(loadMenuItem);

        JMenuItem editGroupsMenuItem = new JMenuItem("Edytuj grupy");
        editGroupsMenuItem.addActionListener(e -> {
            mainPanel.removeAll();
            mainPanel.add(new WorkplaceGroupsPanel(), BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        optionsMenu.add(editGroupsMenuItem);


        setVisible(true);
    }
}
