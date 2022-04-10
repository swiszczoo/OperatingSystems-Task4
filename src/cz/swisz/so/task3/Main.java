package cz.swisz.so.task3;

import cz.swisz.so.task3.algorithms.*;

interface AlgorithmFactory {
    PageReplacementAlgorithm constructInstance();
}

public class Main {
    private static void test(String algName, AlgorithmFactory factory, Process[] processes) {
        double totalErrors = 0.0;

        for (Process proc : processes) {
            Process result = proc.cloneProcess();
            factory.constructInstance().runSimulation(result);
            totalErrors += result.getPageErrors();
        }

        System.out.printf("%s: avg %f errors\n", algName, totalErrors / processes.length);
    }


    public static void main(String[] args) {
        Process[] processes = new Process[15];
        for (int i = 1; i <= 15; i++) {
            processes[i - 1] = Generator.generateProcess(i * 10);
        }

        System.out.println("Test results:");
        test("FIFO", FIFO::new, processes);
        test("OPT", OPT::new, processes);
        test("LRU", LRU::new, processes);
        test("ALRU", ApproxLRU::new, processes);
        test("RAND", RAND::new, processes);
    }
}
