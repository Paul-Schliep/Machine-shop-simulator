package applications;

import dataStructures.LinkedQueue;

public class Job {
    // data members
    LinkedQueue taskQ; // this job's tasks
    int length; // sum of scheduled task times
    int arrivalTime; // arrival time at current queue
    int id; // job identifier

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
        int theTime = ((Task) taskQ.remove()).time;
        length += theTime;
        return theTime;
    }
    
    
    /**
     * move theJob to machine for its next task
     * 
     * @return false iff no next task
     */
    static boolean moveToNextMachine(Job theJob) {
        if (theJob.taskQ.isEmpty()) {// no next task
            System.out.println("Job " + theJob.id + " has completed at "
                    + MachineShopSimulator.timeNow + " Total wait was " + (MachineShopSimulator.timeNow - theJob.length));
            return false;
        }
        // get machine for next task
         int p = ((Task) theJob.taskQ.getFrontElement()).machine;
         // put on machine p's wait queue
         MachineShopSimulator.machine[p].getJobQ().put(theJob);
         theJob.arrivalTime = MachineShopSimulator.timeNow;
         // if p idle, schedule immediately
         if (MachineShopSimulator.eList.nextEventTime(p) == MachineShopSimulator.largeTime) {// machine is idle
        Machine.changeState(p);
         }
         return true;
    }
    
}