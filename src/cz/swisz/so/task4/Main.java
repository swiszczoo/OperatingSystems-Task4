package cz.swisz.so.task4;

import cz.swisz.so.task4.algorithms.*;

import java.util.Random;

interface AlgorithmFactory {
    PageReplacementAlgorithm constructInstance();
}

public class Main {
    private static void test(String algName, AlgorithmFactory factory, Sequence seq) {
        double totalErrors = 0.0;

        Sequence sequence = seq.cloneSequence();
        PageReplacementAlgorithm alg = factory.constructInstance();
        alg.runSimulation(sequence);
        sequence.calcTotalPageErrors();
        totalErrors += sequence.getPageErrors();

        System.out.printf("%s: avg %f errors\n", algName, totalErrors / sequence.getProcesses().size());
        System.out.print("Err per proc: ");
        for (Process process : sequence.getProcesses()) {
            System.out.printf("%d ", process.getPageErrors());
        }
        System.out.println();
        System.out.print("Pages per proc: ");
        for (Process process : sequence.getProcesses()) {
            System.out.printf("%d ", alg.getFramesForProcess(process));
        }
        System.out.println();
        System.out.println();
    }


    public static void main(String[] args) {
        Process[] processes = new Process[15];
        Random rand = new Random();
        for (int i = 1; i <= 15; i++) {
            processes[i - 1] = Generator.generateProcess(rand.nextInt(50) + 75);
        }

        Sequence seq = new Sequence(processes);

        System.out.println("Test results:");
        test("Proportional", Proportional::new, seq);
        test("Equal", Equal::new, seq);
        test("Page fault frequency", PageFaultFrequency::new, seq);
        test("Working set (zone model)", WorkingSet::new, seq);
    }
}
