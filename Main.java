import java.util.Scanner;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import algorithms.*;
import model.*;

public class Main {

    static String algos[] = {"First Come First Serve", "Shortest Job First", "Shortest Remaining Time First", "Round-Robin", "Priority Scheduler", ""};
    static Util util = new Util();
    static ProcessModel processes[];

    public static void main(String[] args) {
        initComponents();
    }

    public static void initComponents() {
        JFrame frame = new JFrame("Process Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(50, 10));

        String[] columns = {"Process", "Arrival Time", "Burst Time"};
        Object[][] data = {};

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBounds(50, 20, 400, 200);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(panel.getWidth(), panel.getHeight()));
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton addProcess = new JButton("Add Process");
        addProcess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRow(model);
            }
        });

        JComboBox algoBox = new JComboBox(algos); 
        algoBox.setSelectedIndex(0);

        JButton clrBtn = new JButton("Clear All");
        JPanel btnPanel = new JPanel(new BorderLayout(20, 20));
      

        JButton runBtn = new JButton("Run");

        GridBagLayout grid = new GridBagLayout();  
        GridBagConstraints gbc = new GridBagConstraints();  
        btnPanel.setLayout(grid);     
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;  
        gbc.gridx = 0;  
        gbc.gridy = 0;  
        btnPanel.add(algoBox, gbc);  
        gbc.gridx = 1;  
        gbc.gridy = 0;  
        btnPanel.add(clrBtn, gbc);  
        gbc.gridx = 0;  
        gbc.gridy = 2;  
        gbc.fill = GridBagConstraints.HORIZONTAL;  
        gbc.gridwidth = 2;  
        btnPanel.add(runBtn, gbc);  

        algoBox.setEditable(false);
        btnPanel.setSize(frame.getWidth(), 50);

        int paddingSize = 20; // Adjust as needed
        btnPanel.setBorder(new EmptyBorder(paddingSize, paddingSize, paddingSize, paddingSize));


        panel.add(addProcess, BorderLayout.NORTH);

        JPanel showProcess = new JPanel( new BorderLayout());
        showProcess.setBorder(BorderFactory.createTitledBorder("Show Process"));
        showProcess.setLayout(new BorderLayout());

        // Create a panel to hold the boxes with FlowLayout
        JPanel boxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // 10 pixels gap

        JScrollPane scrollPaneProcess = new JScrollPane(boxPanel);
        scrollPaneProcess.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // Ensure vertical scrollbar never appears

        showProcess.add(scrollPaneProcess, BorderLayout.CENTER);

        // Set preferred size for showProcess panel (optional)
        showProcess.setPreferredSize(new Dimension(400, 100)); // Adjust size as needed




        /* ============================== Run ======================================================= */

        runBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            /* ========================================================================================== */
                Util util = new Util();
                processes = util.createProcesses(model);
                if(model.getRowCount() == 0) {
                    util.enter_process(frame);
                    return;
                }
                System.out.println("Selected " + algoBox.getSelectedIndex());

                for(int i=0; i<processes.length; i++) {
                    System.out.println(processes[i].getId());
                    System.out.println(processes[i].getArrivalTime());
                    System.out.println(processes[i].getBurstTime());
                }
                /* ========================================================================================== */
                switch(algoBox.getSelectedIndex()){
                    case 0:
                        new FCFS (boxPanel, processes);
                        break;
                    case 1:
                        new SJF (boxPanel, processes);
                        break;
                    case 2:
                        new SRTF (boxPanel, processes);
                        break;
                    case 3:
                        new RR (boxPanel, processes);
                        break;
                    case 4:
                        new PS (boxPanel, processes);
                        break;
                    case 5:
                        break;
                }

        /* =========================================================================================== */
            }
        });

        frame.add(btnPanel, BorderLayout.SOUTH);
        frame.add(panel, BorderLayout.NORTH);
        frame.add(showProcess, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void addRow(DefaultTableModel model) {
        // Prompt the user to enter data for each cell of the new row
        String[] rowData = new String[model.getColumnCount()];
        for (int i = 0; i < rowData.length; i++) {
            rowData[i] = JOptionPane.showInputDialog("Enter data for " + model.getColumnName(i));
        }
        // Add the new row to the table model
        model.addRow(rowData);
    }


}