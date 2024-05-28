package model;
public class ProcessModel {
    int id;
    public int getId() {
        return id;
    }

    int arrivalTime;
    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    int burstTime;
    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int priority;
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int remainingTime;

    public ProcessModel (int id, int arrivalTime, int burstTime, int priority, int remainingTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
        this.remainingTime = remainingTime;
    }
}