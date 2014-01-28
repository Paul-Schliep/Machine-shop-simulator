package applications;

// top-level nested classes
public class Task {
    // data members
    private int machine;
    private int time;

    // constructor
    Task(int theMachine, int theTime) {
        this.machine = theMachine;
        this.time = theTime;
    }

    public int getTime() {
        return time;
    }

    public int getMachine() {
        return machine;
    }
}
