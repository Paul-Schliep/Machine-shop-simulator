package applications;

import dataStructures.LinkedQueue;

public class Job {
    private LinkedQueue taskQ;
    private int totalTime = 0; // sum of scheduled task times
    private int arrivalTime = 0; // arrival time at current queue
    private int id;

    Job(int theId) {
        id = theId;
        taskQ = new LinkedQueue();
    }

    void addTask(int theMachine, int theTime) {
        taskQ.put(new Task(theMachine, theTime));
    }

    int removeNextTask() {
        int theTime = Task.removeTask(taskQ);
        totalTime += theTime; // update job's totalTime
        return theTime;
    }
    
    boolean moveToNextMachine() {
         if(hasNoActiveTask() == false) return false;
         
         Machine nextMachine = MachineShopSimulator.getMachine(Task.getMachineId(taskQ));
         nextMachine.addJob(this);
         this.arrivalTime = MachineShopSimulator.getTimeNow();
         nextMachine.checkIdle();
         return true;
    }


    private boolean hasNoActiveTask() {
        if (this.taskQ.isEmpty()) {
            MachineShopSimulator.jobFinished(id, totalTime);
            return false;
        }
        return true;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    
}