import java.util.LinkedList;
import java.util.Queue;

public class FSCS {
    FSCS(Process[] processes) {
        // Implement the First-Come-First-Served algorithm here
        Queue<Process> queue = new LinkedList<>();
        int currentTime = 0;
        double totalTurnaroundTime = 0;
        double totalWaitingTime = 0;

                // Enqueue processes based on arrival time
                for (Process process : processes) {
                    while (currentTime < process.arrivalTime) {
                        currentTime++;
                    }
                    queue.add(process);
        
                    // Execute the process
                    System.out.println("Time " + currentTime + ": Process " + process.id + " started execution.");
        
                    // Update waiting time and turnaround time
                    totalWaitingTime += currentTime - process.arrivalTime;
                    currentTime += process.burstTime;
                    totalTurnaroundTime += currentTime - process.arrivalTime;
        
                    System.out.println("Time " + currentTime + ": Process " + process.id + " completed execution.");
                }
        
                // Calculate and display average waiting time and average turnaround time
                double avgWaitingTime = totalWaitingTime / processes.length;
                double avgTurnaroundTime = totalTurnaroundTime / processes.length;
                System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
                System.out.println("Average Turnaround Time: " + avgTurnaroundTime);

        //enqueueProcesses(queue, processes);
    }

    Queue<Process> enqueueProcesses(Queue<Process> queue, Process[] processes){

        for (Process process : processes) {
            queue.add(process);
        }
        
        return queue;
    }
}
