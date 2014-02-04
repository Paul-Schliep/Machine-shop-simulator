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
    public Job changeState() {// Task on theMachine has finished,
                                            // schedule next one.
        Job lastJob;
        if (this.activeJob == null) {// in idle or change-over
                                                    // state
            lastJob = null;
            // wait over, ready for new job
            this.getNewJob();
        } else {// task has just finished on machine[theMachine]
                // schedule change-over time
            lastJob = this.activeJob;
            this.activeJob = null;
            MachineShopSimulator.getEventList().setFinishTime(this, MachineShopSimulator.getTimeNow()
                    + this.getChangeTime());
        }

        return lastJob;
    }


    private void getNewJob() {
        if (this.getJobQ().isEmpty()) // no waiting job
            MachineShopSimulator.getEventList().setFinishTime(this, MachineShopSimulator.getLargeTime());
        else {// take job off the queue and work on it
            this.activeJob = (Job) this.getJobQ()
                    .remove();
            this.totalWait = (this.totalWait
                    + (MachineShopSimulator.getTimeNow()
                            - this.activeJob.getArrivalTime()));
            this.numTasks = (this.getNumTasks() + 1);
            
            int time = this.activeJob.removeNextTask();
            MachineShopSimulator.setEventFinishTime(this, time);
        }
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