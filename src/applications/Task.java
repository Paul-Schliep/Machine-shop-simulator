package applications;

import dataStructures.LinkedQueue;

public class Task {
    private int machineId;
    private int time;

    Task(int theMachine, int theTime) {
        this.machineId = theMachine;
        this.time = theTime;
    }

    public static int removeTask(LinkedQueue taskQ) {
        return ((Task) taskQ.remove()).time;
    }

    public static int getMachineId(LinkedQueue taskQ) {
         return ((Task) taskQ.getFrontElement()).machineId;
    }
}
