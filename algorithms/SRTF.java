package algorithms;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import model.*;

public class SRTF {

    DefaultTableModel model;
    JPanel boxPanel;
    ProcessModel processes[];
    Util util;

    public SRTF (DefaultTableModel model, JPanel boxPanel){
        this.model = model;
        this.boxPanel = boxPanel;
        processes = util.createProcesses(model);
        schedule();
    }

    public void schedule() {
      int n = processes.length; // Number of processes
  
      // Sort processes by arrival time
      sortProcessesByArrival(processes);
  
      int currentTime = 0;
      int completed = 0;
      while (completed != n) {
        // Find the process with the shortest remaining time among ready processes
        int minIndex = findShortestRemainingTime(processes, currentTime);
  
        if (minIndex == -1) {
          // No process is ready, increment time
          currentTime++;
        } else {
          // Process found, execute it
          ProcessModel current = processes[minIndex];
          current.remainingTime--;
          currentTime++;
  
          // Process is completed
          if (current.remainingTime == 0) {
            System.out.println("Process " + current.getId() + " completed at time " + currentTime);
            completed++;
          }
        }
      }
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
