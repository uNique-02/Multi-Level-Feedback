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
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import algorithms.*;
import model.*;

public class Main {
    static Util util = new Util();
    static ProcessModel processes[];
    static JComboBox algoBox[];
    static int selectedOption;

    public static void main(String[] args) {
        initComponents();
    }

    public JComboBox createComboBox(String[] algos){

        JComboBox algoBox = new JComboBox(algos); 
        algoBox.setSelectedIndex(0);
        return algoBox;
    }
    public static void initComponents() {
        JFrame frame = new JFrame("Process Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(20, 10));

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

        JButton clrBtn = new JButton("Clear All");
        JPanel btnPanel = new JPanel(new BorderLayout(20, 20));
      

        JButton runBtn = new JButton("Run");

        String[] queueOptions = {"1", "2", "3"};

        // Create a JComboBox with the options array
        JComboBox<String> numberofQueues = new JComboBox<>(queueOptions);
        numberofQueues.setBorder(BorderFactory.createTitledBorder("Number of Queues"));

        JButton okButton = new JButton("OK");

        GridBagLayout grid = new GridBagLayout();  
        GridBagConstraints gbc = new GridBagConstraints();  
        btnPanel.setLayout(grid);     
        gbc.insets = new Insets(5, 10, 5, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;  

        gbc.gridx = 0;  
        gbc.gridy = 0;
        gbc.gridwidth = 2;  
        
        btnPanel.add(numberofQueues, gbc); 

        numberofQueues.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {

                selectedOption = Integer.parseInt(numberofQueues.getSelectedItem().toString());
                System.out.println(selectedOption);

                algoBox = new JComboBox[selectedOption];

                while(selectedOption > 0) {
                    algoBox[selectedOption-1] = util.createBox();
                    gbc.gridx = 0;  
                    gbc.gridy = selectedOption;  
                    btnPanel.add(algoBox[selectedOption-1], gbc); 
                    selectedOption--; 
                    btnPanel.revalidate();
                    btnPanel.repaint();

                    System.out.println("kim");
                }
            }
        });
        
        gbc.gridx = 3;  
        gbc.gridy = 0;  
        btnPanel.add(okButton, gbc);
        
        gbc.gridx = 0;  
        gbc.gridy = selectedOption+6;  
        gbc.gridwidth = 2; 
        btnPanel.add(clrBtn, gbc);  

        gbc.gridx = 2;  
        gbc.gridy = selectedOption+6;   
        gbc.gridwidth = 1;  
        btnPanel.add(runBtn, gbc);  


        btnPanel.setSize(frame.getWidth(), 50);

        int paddingSize = 10; // Adjust as needed
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
        showProcess.setPreferredSize(new Dimension(400, 200)); // Adjust size as needed




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
                System.out.println("Selected " + algoBox[0].getSelectedIndex());

                for(int i=0; i<processes.length; i++) {
                    System.out.println(processes[i].getId());
                    System.out.println(processes[i].getArrivalTime());
                    System.out.println(processes[i].getBurstTime());
                }
                /* ========================================================================================== */
                boxPanel.removeAll();
                boxPanel.revalidate();
                boxPanel.repaint();

                switch(algoBox[0].getSelectedIndex()){
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


        frame.add(panel, BorderLayout.NORTH);
        frame.add(showProcess, BorderLayout.CENTER);
        frame.add(btnPanel, BorderLayout.SOUTH);
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