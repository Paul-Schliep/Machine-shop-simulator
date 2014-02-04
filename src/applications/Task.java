package applications;

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

    public int getTime() {
        return time;
    }

    public int getMachineId() {
        return machineId;
    }
}
