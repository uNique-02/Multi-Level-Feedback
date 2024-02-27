import java.util.Scanner;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {

        initComponents();
    }

        public static void initComponents() {
        JFrame frame = new JFrame("Process Table");
        frame.setSize(500, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(50, 10));

        String[] columns = {"Process", "Arrival Time", "Burst Time"};
        Object[][] data = {};

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBounds(50, 20, 400, 200);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton addProcess = new JButton("Add Process");
        addProcess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRow(model);
            }
        });

        JButton run = new JButton("Run");

        panel.add(addProcess, BorderLayout.NORTH);
        panel.add(run, BorderLayout.SOUTH);

        JPanel showProcess = new JPanel();
        showProcess.setLayout(new BorderLayout());
        showProcess.setSize(400, 200);

        // Create a panel to hold the boxes with FlowLayout
        JPanel boxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // 10 pixels gap
        showProcess.add(boxPanel, BorderLayout.CENTER);


        /* ============================== Run ======================================================= */

        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            /* ========================================================================================== */

            int delay = 1000; // Delay in milliseconds (1 second)
            Timer timer = new Timer(delay, new ActionListener() {
                private int i = 0, j=0, k=Integer.parseInt(model.getValueAt(j, 2).toString()); // Counter to track the number of boxes added
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (i < gettotalTime(model, delay)) {
                        Random random = new Random();
                        Color color = color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));;
                        if(i>=k){
                            j++;
                            k = k+Integer.parseInt(model.getValueAt(j, 2).toString());
                        }
                        JPanel box = new JPanel();
                        JLabel label = new JLabel("" + (j));
                        box.setLayout(new BorderLayout());
                        box.add(label, BorderLayout.CENTER);
                        box.setPreferredSize(new Dimension(12, 30));
                        box.setBorder(BorderFactory.createLineBorder(color));
                        box.setBackground(color);
                        boxPanel.add(box); // Add the box to the panel
                        boxPanel.revalidate(); // Revalidate the panel to reflect the changes
                        i++;
                    } else {
                        ((Timer) e.getSource()).stop(); // Stop the timer when all boxes are added
                    }
                }
            });
            timer.start(); // Start the timer


        /* =========================================================================================== */
            }
        });


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

    static int gettotalTime (DefaultTableModel model, int i){

        System.out.println("Count: " + model.getRowCount());
        int total = 0;
        for (int row = 0; row < model.getRowCount(); row++) {
            Object value = model.getValueAt(row, 2);
            total += Integer.parseInt(value.toString());

            System.err.println("Value: " + value.toString() + " Total: " + total);
        }

        return total;
    }
}