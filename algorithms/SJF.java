package algorithms;

import model.ProcessModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class SJF {

    JPanel boxPanel;
    ProcessModel process[];

    private List<ProcessModel> processes;

    public SJF() {
        processes = new ArrayList<>();        
    }

    public void addProcess(ProcessModel p){
        processes.add(p);
    }

    public void schedule(){
        //Sort processes by burst time
        processes.sort(Comparator.comparingInt(p -> p.burstTime));

        int currentTime = 0;

        for(ProcessModel p : processes){
            if (currentTime < p.arrivalTime){
                currentTime = p.arrivalTime;
            }

            p.waitingTime = currentTime - p.arrivalTime;
            p.turnaroundTime = p.waitingTime + p.burstTime;
            currentTime += p.burstTime;
        }
    }

    public void printSchedule(){
        System.out.println("ID\tArrival\tBurst\tWaiting\tTurnaround");
        for(ProcessModel p : processes){
            System.out.println(p.id + "\t" + p.arrivalTime + "\t" + p.burstTime + "\t" + p.waitingTime + "\t" + p.turnaroundTime);
        }
    }

    public double getAverageWaitingTime(){
        int totalWaitingTime = 0;
        for(ProcessModel p : processes){
            totalWaitingTime += p.waitingTime;
        }

        return (double) totalWaitingTime / processes.size();
    }

    public double getAverageTurnaroundTime(){
        int totalTurnaroundTime = 0;
        for(ProcessModel p : processes){
            totalTurnaroundTime += p.turnaroundTime;
        }

        return (double) totalTurnaroundTime / processes.size();
    }
    
    
}

/* SJF Algo
 * 
 * SJF_Scheduling(Processes):
    Sort Processes based on Burst Time (Shortest Job First)
    currentTime = 0
    for each process in Processes:
        if process.arrivalTime > currentTime:
            currentTime = process.arrivalTime
        execute process for process.burstTime units of time
        process.completionTime = currentTime + process.burstTime
        currentTime = process.completionTime
    return Processes

 */
