package applications;

import dataStructures.LinkedQueue;

public class Job {
    // data members
    private LinkedQueue taskQ; // this job's tasks
    private int length; // sum of scheduled task times
    private int arrivalTime; // arrival time at current queue
    private int id; // job identifier

    // constructor
    Job(int theId) {
        id = theId;
        taskQ = new LinkedQueue();
        // length and arrivalTime have default value 0
    }

    // other methods
    void addTask(int theMachine, int theTime) {
        taskQ.put(new Task(theMachine, theTime));
    }

    /**
     * remove next task of job and return its time also update length
     */
    int removeNextTask() {
        int theTime = ((Task) taskQ.remove()).getTime();
        length += theTime;
        return theTime;
    }
    
    
    /**
     * move theJob to machine for its next task
     * 
     * @return false iff no next task
     */
    boolean moveToNextMachine() {
         if(hasNoActiveTask() == false) return false;
        // get machine for next task
         int p = ((Task) this.taskQ.getFrontElement()).getMachine();
         // put on machine p's wait queue
         MachineShopSimulator.getMachine(p).getJobQ().put(this);
         this.arrivalTime = MachineShopSimulator.getTimeNow();
         // if p idle, schedule immediately
         checkIdle(p);
         return true;
    }

    private void checkIdle(int p) {
        // machine is idle
        if (MachineShopSimulator.isJobIdle(p)) {
             Machine currentMachine = MachineShopSimulator.getMachine(p);
             currentMachine.changeState();
         }
    }

    private boolean hasNoActiveTask() {
        if (this.taskQ.isEmpty()) {// no next task
            MachineShopSimulator.jobDone(id, length);
            return false;
        }
        return true;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    
}