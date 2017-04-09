import java.util.LinkedList;
import java.util.Queue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tin
 */
public class Process implements Comparable<Process>{
    private int processCycle;
    private int processTotalCycle;
    private int processStatus;
    private int processID;
    private Queue <String> blockQueue = new LinkedList();
    
    public Process(String input){
        String values[] = input.split(",");
        processID = Integer.parseInt(values[0]);
        processTotalCycle = Integer.parseInt(values[1]);
        processCycle = 0;
    }
    
    public int getProcessID(){
        return processID;
    }
    
    public int getProcessCycle(){
        return processCycle;
    }
    
    public void updateProcessCycle(int currentProcessCycle){
        processCycle = currentProcessCycle;
    }
    
    public int getTotalProcessCycle(){
        return processTotalCycle;
    }
    
    public String getProcessStatus(){
        String status = "";
        switch (processStatus){
            case 0:
                status = "running";
                break;
            case 1:
                status = "ready";
                break;
            case 2:
                status = "blocked";
                break;
            default:
                status = "done";
                break;
        }
        return status;
    }
    
    public void setProcessStatus(int newProcessStatus){
        processStatus = newProcessStatus;
    }

    @Override
    public int compareTo(Process p) {
        return (this.processTotalCycle - p.processTotalCycle);
    }
}
