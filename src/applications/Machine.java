package applications;

import dataStructures.LinkedQueue;

public class Machine {
    private LinkedQueue jobQ;
    private int changeTime;
    private int totalWait;
    private int numTasks;
    private Job activeJob;
    private int id;

    public Machine(int id) {
        this.jobQ = new LinkedQueue();
        this.id = id;
    }
    
    // machine is idle, schedule!
    public void checkIdle() {
        if (MachineShopSimulator.isMachineIdle(id)) {
             this.changeState();
         }
    }
    
    public Job changeState() {
        Job lastJob;
        // in idle or change-over state
        if (this.activeJob == null) {
            lastJob = null;
            this.getNewJob();
        } 
        // task has just finished on machine, schedule change-over time
        else {
            lastJob = this.activeJob;
            this.activeJob = null;
            MachineShopSimulator.setFinishTime(id, this.changeTime);
        }

        return lastJob;
    }

    private void getNewJob() {
        if (this.jobQ.isEmpty()) MachineShopSimulator.noMoreJobs(id);
        
        else {// do next job
            this.activeJob = (Job) this.jobQ.remove();
            this.totalWait = (this.totalWait + (MachineShopSimulator.getTimeNow() - this.activeJob.getArrivalTime()));
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
        System.out.println("Machine " + (id+1) + " completed " + numTasks + " tasks");
        System.out.println("The total wait time was " + totalWait);
        System.out.println();
    }
}