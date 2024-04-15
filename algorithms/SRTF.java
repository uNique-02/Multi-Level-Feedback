package algorithms;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.*;

public class SRTF {

    DefaultTableModel model;
    JPanel boxPanel;
    ProcessModel processes[];
    Util util;

    public SRTF (JPanel boxPanel, ProcessModel[] processes){
        this.processes = processes;
        this.boxPanel = boxPanel;
        schedule();
    }


    public void schedule() {
      int n = processes.length; // Number of processes
      int delay = 1000; // Delay in milliseconds (1 second)
      AtomicInteger currentTime = new AtomicInteger(0); // AtomicInteger to handle mutability
  
      Timer timer = new Timer();
  
      TimerTask task = new TimerTask() {
          @Override
          public void run() {
              // Sort processes by arrival time
              sortProcessesByArrival(processes);
  
              int completed = 0; // Move completed inside run() method
  
              // Find the process with the shortest remaining time among ready processes
              int minIndex = findShortestRemainingTime(processes, currentTime.get());
  
              if (minIndex == -1) {
                  // No process is ready, increment time
                  currentTime.incrementAndGet();
              } else {
                  // Process found, execute it
                  ProcessModel current = processes[minIndex];
                  current.remainingTime--;
                  currentTime.incrementAndGet();

                  Random random = new Random();
                        Color color = color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                        JPanel box = new JPanel();
                        JLabel label = new JLabel("" + (current.getId()));
                        box.setLayout(new BorderLayout());
                        box.add(label, BorderLayout.CENTER);
                        box.setPreferredSize(new Dimension(12, 30));
                        box.setBorder(BorderFactory.createLineBorder(color));
                        box.setBackground(color);
                        boxPanel.add(box); // Add the box to the panel
                        boxPanel.revalidate(); // Revalidate the panel to reflect the changes
                  // Process is completed
                  if (current.remainingTime == 0) {
                      System.out.println("Process " + current.getId() + " completed at time " + currentTime.get());
                      completed++;
                  }
              }
  
              // Check if all processes are completed
              if (completed == n) {
                  cancel(); // Stop the timer
              }
          }
      };
  
      timer.scheduleAtFixedRate(task, delay, delay); // Schedule the task to run every 'delay' milliseconds
  }
  
    // Sorts processes by arrival time
    private static void sortProcessesByArrival(ProcessModel[] processes) {
      for (int i = 0; i < processes.length - 1; i++) {
        for (int j = 0; j < processes.length - i - 1; j++) {
          if (processes[j].getArrivalTime() > processes[j + 1].getArrivalTime()) {
            ProcessModel temp = processes[j];
            processes[j] = processes[j + 1];
            processes[j + 1] = temp;
          }
        }
      }
    }
  
    // Finds the process with the shortest remaining time among ready processes
    private static int findShortestRemainingTime(ProcessModel[] processes, int currentTime) {
      int minIndex = -1;
      int minRemainingTime = Integer.MAX_VALUE;
      for (int i = 0; i < processes.length; i++) {
        if (processes[i].getArrivalTime() <= currentTime && processes[i].remainingTime > 0) {
          if (processes[i].remainingTime < minRemainingTime) {
            minIndex = i;
            minRemainingTime = processes[i].remainingTime;
          }
        }
      }
      return minIndex;
    }
  }
