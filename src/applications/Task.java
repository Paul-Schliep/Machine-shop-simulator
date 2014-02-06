package applications;

import dataStructures.LinkedQueue;

// top-level nested classes
public class Task {
    // data members
    private int machineId;
    private int time;

    // constructor
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
