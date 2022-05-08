package cz.swisz.so.task4.algorithms;

import cz.swisz.so.task4.Process;
import cz.swisz.so.task4.Sequence;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class PageFaultFrequency extends LRU {
    private HashMap<Process, Queue<Integer>> _errors;
    private static double MIN_THRESHOLD = 0.005;
    private static double MAX_THRESHOLD = 0.04;

    public PageFaultFrequency() {
        _errors = new HashMap<>();
    }

    @Override
    protected void startSimulation(Sequence sequence) {
        // Give each process three frames (initially)

        for (Process p : sequence.getProcesses()) {
            for (int i = 0; i < 3; i++) {
                appendFrame(p);
            }
        }

        super.startSimulation(sequence);
    }

    @Override
    protected void requestSwap(Process proc, int pageToBeSwapped) {
        super.requestSwap(proc, pageToBeSwapped);

        // A page error occurred

        if (!_errors.containsKey(proc)) {
            _errors.put(proc, new LinkedList<>());
        }

        Queue<Integer> q = _errors.get(proc);
        q.add(getCurrentIteration(proc));

        if (q.size() > 10) {
            Integer last = q.poll();

            double currentFreq = 10.0 / (getCurrentIteration(proc) - last);

            if (currentFreq > MAX_THRESHOLD) {
                appendFrame(proc);
            } else if(currentFreq < MIN_THRESHOLD) {
                discardFrame(proc);
            }
        }
    }
}
