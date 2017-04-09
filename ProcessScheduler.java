/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Tin
 */
public class ProcessScheduler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String filename = args[0];

        Process[] process = readAndLoad(filename);
        System.out.println("Running First-come, first-served scheduler.");
        fifoProcess(process);
        process = readAndLoad(filename);
        System.out.println("\nRunning shortest first scheduler.");
        shortestFirst(process);
        process = readAndLoad(filename);
        roundRobin(process, 50);
        process = readAndLoad(filename);
        roundRobin(process, 100);
        process = readAndLoad(filename);
        randomScheduler(process, 50);

    }

    private static Process[] readAndLoad(String filename) {
        Process[] process = null;
        try {
            LinkedList<String> data = new LinkedList();
            File file = new File(filename);
            Scanner is = new Scanner(file);
            while (is.hasNext()) {
                data.add(is.next());
            }
            process = new Process[data.size()];
            for (int i = 0; i < process.length; i++) {
                process[i] = new Process(data.get(i));
            }

        } catch (FileNotFoundException ex) {
            System.err.println("File not found");
        }
        return process;
    }

    private static void fifoProcess(Process[] process) {
        int totalCycle = 0;
        int turnaround = 0;
        for (int i = 0; i < process.length; i++) {
            if (!process[i].getProcessStatus().equalsIgnoreCase("done")) {
                totalCycle += process[i].getTotalProcessCycle();
                turnaround += totalCycle;
                System.out.println("Process " + process[i].getProcessID() + " finished on cycle " + totalCycle);
            }
        }
        turnaround = turnaround / process.length;
        System.out.println("Average turnaround time: " + turnaround);
    }

    private static void shortestFirst(Process[] process) {
        Arrays.sort(process);
        fifoProcess(process);
    }

    private static void roundRobin(Process[] process, int quantum) {
        System.out.println("\nRunning round robin scheduler with quantum " + quantum);
        int totalCycle = 0;
        int finish = 0;
        int turnaround = 0;
        while (true) {
            for (int i = 0; i < process.length; i++) {
                if (process[i].getProcessCycle() != process[i].getTotalProcessCycle()) {
                    int cycle = quantum;
                    if ((process[i].getTotalProcessCycle() - process[i].getProcessCycle()) < 100) {
                        cycle = process[i].getTotalProcessCycle() - process[i].getProcessCycle();
                    }
                    totalCycle += cycle;         
                    int newPC = process[i].getProcessCycle() + cycle;
                    process[i].updateProcessCycle(newPC);
                    if (process[i].getProcessCycle() == process[i].getTotalProcessCycle()) {
                        System.out.println("Process " + process[i].getProcessID() + " finished on cycle " + totalCycle);
                        turnaround += totalCycle;
                        finish++;
                    }
                }
            }
            if (finish == process.length) {
                break;
            }
        }
        turnaround = turnaround / process.length;
        System.out.println("Average turnaround time: " + turnaround);
    }

    private static void randomScheduler(Process[] process, int quantum) {
    System.out.println("\nRunning random scheduler with quantum " + quantum);
        int totalCycles = 0;
        int totalCycle = 0;
        int finish = 0;
        int i = 0;
        int turnaround = 0;
        for (int j = 0; j < process.length; j++) {
            totalCycles += process[j].getTotalProcessCycle();
        }
        while (true) {
            double percent = 0;
            double[] percentile = new double[process.length];
            for (int j = 0; j < process.length; j++) {
                percent += (((double) process[j].getTotalProcessCycle() - (double) process[j].getProcessCycle()) / (double) totalCycles);
                System.out.println(percent);
                percentile[j] = percent;
            }
            double x = Math.random();
            for (int j = 0; j < percentile.length; j++) {
                if (x <= percentile[j]) {
                    i = j;
                    break;
                }
            }
            if (process[i].getProcessCycle() != process[i].getTotalProcessCycle()) {
                int cycle = quantum;
                if ((process[i].getTotalProcessCycle() - process[i].getProcessCycle()) < 100) {
                    cycle = process[i].getTotalProcessCycle() - process[i].getProcessCycle();
                }
                totalCycle += cycle;
                totalCycles -= cycle;
                int newPC = process[i].getProcessCycle() + cycle;
                process[i].updateProcessCycle(newPC);
                if (process[i].getProcessCycle() == process[i].getTotalProcessCycle()) {
                    System.out.println("Process " + process[i].getProcessID() + " finished on cycle " + totalCycle);
                    finish++;
                    turnaround += totalCycle;
                }
            }
            if (finish == process.length) {
                break;
            }
        }
        turnaround = turnaround / process.length;
        System.out.println("Average turnaround time: " + turnaround);
    }
}
