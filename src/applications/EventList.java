package applications;

public class EventList {
    private int[] finishTimeArray;
    private static int machineIdle = Integer.MAX_VALUE; // machines that are set to this are idle


    EventList(int theNumMachines) {
        if (theNumMachines < 1) throw new IllegalArgumentException("number of machines and jobs must be >= 1");
        finishTimeArray = new int[theNumMachines];

        // all machines start idle, initialize with large finish time
        for (int i = 0; i < theNumMachines; i++) finishTimeArray[i] = machineIdle;
    }

    int nextMachineOnEvent() {
        // find first machine to finish, this is the machine with smallest finish time
        int machineId = 0;
        int finishTime = finishTimeArray[0];
        for (int i = 1; i < finishTimeArray.length; i++)
            if (finishTimeArray[i] < finishTime) {// i finishes earlier
                machineId = i;
                finishTime = finishTimeArray[i];
            }
        return machineId;
    }

    int nextEventTime(int theMachine) {
        return finishTimeArray[theMachine];
    }

    void setFinishTime(int machineId, int theTime) {
        finishTimeArray[machineId] = theTime;
    }
}
