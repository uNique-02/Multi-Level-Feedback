package algorithms;

import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.ProcessModel;

public class FCFS {
    JPanel boxPanel;
    ProcessModel[] process;
    private int currentTime = 0; // Declare currentTime as an instance variable

    public FCFS(JPanel boxPanel, ProcessModel[] process) {
        this.boxPanel = boxPanel;
        this.process = process;
        schedule();
    }

    public void schedule() {
        int delay = 1000; // Delay in milliseconds (1 second)
        Timer timer = new Timer(delay, new ActionListener() {
            private int i = 0;
            private int j = 0;
            private int k = process[j].getBurstTime(); // Counter to track the number of boxes added

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Current k: " + k + " Current j: " + j);
                
                if (j < process.length && currentTime >= process[j].getArrivalTime()) {
                    if (i < gettotalTime()) {
                        Random random = new Random();
                        Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));

                        if (i < k) {
                            JPanel box = new JPanel();
                            JLabel label = new JLabel("" + process[j].getId());
                            box.setLayout(new BorderLayout());
                            box.add(label, BorderLayout.CENTER);
                            box.setPreferredSize(new Dimension(12, 30));
                            box.setBorder(BorderFactory.createLineBorder(color));
                            box.setBackground(color);
                            boxPanel.add(box); // Add the box to the panel
                            boxPanel.revalidate(); // Revalidate the panel to reflect the changes
                            i++;
                        } else {
                            j++;
                            if (j < process.length) {
                                k = i + process[j].getBurstTime(); // Update k to the next process's burst time
                            }
                        }
                    } else {
                        ((Timer) e.getSource()).stop(); // Stop the timer when all boxes are added
                    }
                }
                currentTime++;
            }
        });

        timer.start(); // Start the timer
    }

    int gettotalTime() {
        int total = 0;
        for (ProcessModel p : process) {
            total += p.getBurstTime();
        }
        return total;
    }
}
