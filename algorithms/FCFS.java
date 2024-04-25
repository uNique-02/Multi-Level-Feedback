package algorithms;

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
import model.*;

public class FCFS {
    JPanel boxPanel;
    ProcessModel process[];

    public FCFS (JPanel boxPanel, ProcessModel[] process) {
        this.boxPanel = boxPanel;
        this.process = process;
        schedule();
    }

    void schedule(){
            int delay = 1000; // Delay in milliseconds (1 second)
            Timer timer = new Timer(delay, new ActionListener() {
                private int i = 0, j=0, k=process[j].getBurstTime(); // Counter to track the number of boxes added
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Current k: " + k + " Current j: " + j);
                    if (i < gettotalTime(delay)) {
                        Random random = new Random();
                        Color color = color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                        if(i>=k){
                            j++;
                            k = k+process[j].getBurstTime();
                        }
                        JPanel box = new JPanel();
                        JLabel label = new JLabel("" + (process[j].getId()));
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
    int gettotalTime (int i){
        int total = 0;
        for (int row = 0; row < process.length; row++) {
            int value = process[row].getBurstTime();
            total += value;
        }

        return total;
    }
}
