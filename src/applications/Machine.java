package applications;

import dataStructures.LinkedQueue;

public class Machine {
    // data members
    private LinkedQueue jobQ; // queue of waiting jobs for this machine
    private int changeTime; // machine change-over time
    private int totalWait; // total delay at this machine
    private int numTasks; // number of tasks processed on this machine
    private Job activeJob; // job currently active on this machine

    // constructor
    public Machine() {
        this.jobQ = new LinkedQueue();
    }
    
    
    /**
     * change the state of theMachine
     * 
     * @return last job run on this machine
     */
    static Job changeState(int theMachine) {// Task on theMachine has finished,
                                            // schedule next one.
        Job lastJob;
        Machine currentMachine = MachineShopSimulator.getMachine()[theMachine];
        if (currentMachine.activeJob == null) {// in idle or change-over
                                                    // state
            lastJob = null;
            // wait over, ready for new job
            if (currentMachine.getJobQ().isEmpty()) // no waiting job
                MachineShopSimulator.geteList().setFinishTime(theMachine, MachineShopSimulator.getLargeTime());
            else {// take job off the queue and work on it
                currentMachine.activeJob = (Job) currentMachine.getJobQ()
                        .remove();
                currentMachine.totalWait = (currentMachine.totalWait
                        + (MachineShopSimulator.getTimeNow()
                                - currentMachine.activeJob.getArrivalTime()));
                currentMachine.numTasks = (currentMachine.getNumTasks() + 1);
                int t = currentMachine.activeJob.removeNextTask();
                MachineShopSimulator.geteList().setFinishTime(theMachine, MachineShopSimulator.getTimeNow() + t);
            }
        } else {// task has just finished on machine[theMachine]
                // schedule change-over time
            lastJob = currentMachine.activeJob;
            currentMachine.activeJob = null;
            MachineShopSimulator.geteList().setFinishTime(theMachine, MachineShopSimulator.getTimeNow()
                    + currentMachine.getChangeTime());
        }

        return lastJob;
    }


    public LinkedQueue getJobQ() {
        return jobQ;
    }


    public int getChangeTime() {
        return changeTime;
    }


    public void setChangeTime(int changeTime) {
        this.changeTime = changeTime;
    }


    public int getNumTasks() {
        return numTasks;
    }


    public int getTotalWait() {
        return totalWait;
    }
}