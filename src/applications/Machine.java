package applications;

import dataStructures.LinkedQueue;

public class Machine {
    // data members
    private LinkedQueue jobQ; // queue of waiting jobs for this machine
    private int changeTime; // machine change-over time
    private int totalWait; // total delay at this machine
    private int numTasks; // number of tasks processed on this machine
    private Job activeJob; // job currently active on this machine
    private int id; // the machine's id number

    // constructor
    public Machine(int id) {
        this.jobQ = new LinkedQueue();
        this.id = id;
    }
    
    
    /**
     * change the state of theMachine
     * 
     * @return last job run on this machine
     */
    public Job changeState() {
        Job lastJob;
        // in idle or change-over state
        if (this.activeJob == null) {
            lastJob = null;
            // wait over, ready for new job
            this.getNewJob();
        } 
        // task has just finished on machine[theMachine]
        // schedule change-over time
        else {
            lastJob = this.activeJob;
            this.activeJob = null;
            MachineShopSimulator.setFinishTime(id, this.getChangeTime());
        }

        return lastJob;
    }


    private void getNewJob() {
        // no waiting job
        if (this.getJobQ().isEmpty())
            MachineShopSimulator.noMoreJobs(id);
        
        // take job off the queue and work on it
        else {
            this.activeJob = (Job) this.getJobQ()
                    .remove();
            this.totalWait = (this.totalWait
                    + (MachineShopSimulator.getTimeNow()
                            - this.activeJob.getArrivalTime()));
            this.numTasks = (this.getNumTasks() + 1);
            
            int time = this.activeJob.removeNextTask();
            MachineShopSimulator.setFinishTime(id, time);
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