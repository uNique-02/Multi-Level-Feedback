import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FSCS {

    DefaultTableModel model;
    JPanel boxPanel;
    Process process[];

    FSCS (DefaultTableModel model, JPanel boxPanel){
        this.model = model;
        this.boxPanel = boxPanel;
        schedule();
    }

    void schedule(){
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
    }
    static int gettotalTime (DefaultTableModel model, int i){
        int total = 0;
        for (int row = 0; row < model.getRowCount(); row++) {
            Object value = model.getValueAt(row, 2);
            total += Integer.parseInt(value.toString());
        }

        return total;
    }
}
