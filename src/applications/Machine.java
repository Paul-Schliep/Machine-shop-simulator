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
    
    public void checkIdle() {
        // machine is idle, schedule!
        if (MachineShopSimulator.isMachineIdle(id)) {
             this.changeState();
         }
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
            MachineShopSimulator.setFinishTime(id, this.changeTime);
        }

        return lastJob;
    }

    private void getNewJob() {
        // no waiting job
        if (this.jobQ.isEmpty())
            MachineShopSimulator.noMoreJobs(id);
        
        // take job off the queue and work on it
        else {
            this.activeJob = (Job) this.jobQ
                    .remove();
            this.totalWait = (this.totalWait
                    + (MachineShopSimulator.getTimeNow()
                            - this.activeJob.getArrivalTime()));
            this.numTasks = (this.numTasks + 1);
            
            int time = this.activeJob.removeNextTask();
            MachineShopSimulator.setFinishTime(id, time);
        }
    }

    public void addJob(Job theJob){
        jobQ.put(theJob);
    }

    public void setChangeTime(int changeTime) {
        this.changeTime = changeTime;
    }
    
    public void stats() {
        System.out.println("Machine " + id + " completed "
                + numTasks + " tasks");
        System.out.println("The total wait time was "
                + totalWait);
        System.out.println();
    }
}