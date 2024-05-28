package model;

public class Queues {
    private QueueOptions queueOptions;
    private int timeQuantum;
    private int priority; 

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
        this.setTimeQuantum(priority);
    }

    public int getTimeQuantum() {
        return timeQuantum;
    }

    public void setTimeQuantum(int priority) {
        this.timeQuantum = 2^(priority + 1);
    }

    public QueueOptions getQueueOptions() {
        return queueOptions;
    }

    public void setQueueOptions(QueueOptions queueOptions) {
        this.queueOptions = queueOptions;
    }
}
