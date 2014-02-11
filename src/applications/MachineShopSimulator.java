package applications;

import utilities.MyInputStream;
import exceptions.MyInputException;

public class MachineShopSimulator {
    
    // data members of MachineShopSimulator
    private static int currentTime; 
    private static int numMachines;
    private static int numJobs;
    private static EventList eventList;
    private static Machine[] machineArray; 

    /** input machine shop data */
    static void inputData() {
        MyInputStream keyboard = new MyInputStream();

        System.out.println("Enter number of machines and jobs");
        numMachines = keyboard.readInteger();
        numJobs = keyboard.readInteger();
        if (numMachines < 1 || numJobs < 1)
            throw new MyInputException("number of machines and jobs must be >= 1");
        
        createEventAndMachines();

        System.out.println("Enter change-over times for machines");
        setChangeOverTime(keyboard);

        createAndSetJobs(keyboard);
    }

    private static void createAndSetJobs(MyInputStream keyboard) {
        Job theJob;
        for (int i = 0; i < numJobs; i++) {
            System.out.println("Enter number of tasks for job " + (i+1));
            int firstMachine = 0;

            // create the job
            theJob = new Job(i);
            System.out.println("Enter the tasks (machine, time)"
                    + " in process order");
            firstMachine = setTasks(keyboard, theJob); 
            // set job
            machineArray[firstMachine].addJob(theJob);
        }
    }

    private static int setTasks(MyInputStream keyboard, Job theJob) {
        int theMachine, theTaskTime, firstMachine = -1;
        
        int numTasks = keyboard.readInteger(); // number of tasks
        if (numTasks < 1) throw new MyInputException("each job must have >= 1 task");
        
        for (int task = 0; task < numTasks; task++) {
            theMachine = keyboard.readInteger()-1;
            theTaskTime = keyboard.readInteger();
            if (theMachine < 0 || theMachine > numMachines || theTaskTime < 1) { 
                throw new MyInputException("bad machine number or task time");
            }
            if (task == 0) firstMachine = theMachine; // job's first machine
            theJob.addTask(theMachine, theTaskTime); // add to job
        }
        return firstMachine;
    }

    private static void setChangeOverTime(MyInputStream keyboard) {
        for (int i = 0; i < numMachines; i++) {
            int ct = keyboard.readInteger();
            if (ct < 0)
                throw new MyInputException("change-over time must be >= 0");
            getMachine(i).setChangeTime(ct);
        }
    }

    private static void createEventAndMachines() {
        eventList = new EventList(numMachines);
        machineArray = new Machine[numMachines];
        for (int i = 0; i < numMachines; i++) machineArray[i] = new Machine(i);
    }
    
    public static Machine getMachine(int machineNum) {
        return machineArray[machineNum];
    }
    
    public static int getTimeNow() {
        return currentTime;
    }
    
    public static void setFinishTime(int machineId, int time) {
        eventList.setFinishTime(machineId, currentTime + time);
    }
    
    public static void noMoreJobs(int machineId) {
        eventList.setFinishTime(machineId, Integer.MAX_VALUE);
    }
    
    public static boolean isMachineIdle(int p){
        return eventList.nextEventTime(p) == Integer.MAX_VALUE;
    }
    
    public static void jobFinished(int jobID, int jobLength){
        System.out.println("Job " + (jobID+1) + " has completed at " + currentTime + " Total wait was " + (currentTime - jobLength));
    }

    /** load first jobs onto each machine */
    static void startShop() {
        for (Machine machine: machineArray){
            machine.changeState();
        }
    }

    /** process all jobs to completion */
    static void simulate() {
        while (numJobs > 0) {
            int nextToFinish = eventList.nextMachineOnEvent();
            currentTime = eventList.nextEventTime(nextToFinish);
            // change job on machine nextToFinish
                        
            Job theJob = machineArray[nextToFinish].changeState();
            // move theJob to its next machine
            // decrement numJobs if theJob has finished
            if (theJob != null && !theJob.moveToNextMachine()) numJobs--;
        }
    }

    /** output wait times at machines */
    static void outputStatistics() {
        System.out.println("Finish time = " + currentTime);
        for (Machine machine: machineArray){
            machine.stats();
        }
    }
    
    /** entry point for machine shop simulator */
    public static void main(String[] args) {
        currentTime = 0;
        inputData(); // get machine and job data
        startShop(); // initial machine loading
        simulate(); // run all jobs through shop
        outputStatistics(); // output machine wait times
    }
    
}
