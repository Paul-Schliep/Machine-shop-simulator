package applications;

import dataStructures.LinkedQueue;

public class Machine {
    // data members
    LinkedQueue jobQ; // queue of waiting jobs for this machine
    int changeTime; // machine change-over time
    int totalWait; // total delay at this machine
    int numTasks; // number of tasks processed on this machine
    Job activeJob; // job currently active on this machine

    // constructor
    public Machine() {
        jobQ = new LinkedQueue();
    }
    
    
    /**
     * change the state of theMachine
     * 
     * @return last job run on this machine
     */
    static Job changeState(int theMachine) {// Task on theMachine has finished,
                                            // schedule next one.
        Job lastJob;
        if (MachineShopSimulator.machine[theMachine].activeJob == null) {// in idle or change-over
                                                    // state
            lastJob = null;
            // wait over, ready for new job
            if (MachineShopSimulator.machine[theMachine].jobQ.isEmpty()) // no waiting job
                MachineShopSimulator.eList.setFinishTime(theMachine, MachineShopSimulator.largeTime);
            else {// take job off the queue and work on it
                MachineShopSimulator.machine[theMachine].activeJob = (Job) MachineShopSimulator.machine[theMachine].jobQ
                        .remove();
                MachineShopSimulator.machine[theMachine].totalWait += MachineShopSimulator.timeNow
                        - MachineShopSimulator.machine[theMachine].activeJob.arrivalTime;
                MachineShopSimulator.machine[theMachine].numTasks++;
                int t = MachineShopSimulator.machine[theMachine].activeJob.removeNextTask();
                MachineShopSimulator.eList.setFinishTime(theMachine, MachineShopSimulator.timeNow + t);
            }
        } else {// task has just finished on machine[theMachine]
                // schedule change-over time
            lastJob = MachineShopSimulator.machine[theMachine].activeJob;
            MachineShopSimulator.machine[theMachine].activeJob = null;
            MachineShopSimulator.eList.setFinishTime(theMachine, MachineShopSimulator.timeNow
                    + MachineShopSimulator.machine[theMachine].changeTime);
        }

        return lastJob;
    }
    
}