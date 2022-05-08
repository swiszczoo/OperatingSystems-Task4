package cz.swisz.so.task4.algorithms;

import cz.swisz.so.task4.Process;
import cz.swisz.so.task4.Sequence;
import cz.swisz.so.task4.Utils;

public class Proportional extends LRU {
    @Override
    protected void startSimulation(Sequence sequence) {
        long totalCalls = 0;
        for (Process p : sequence.getProcesses()) {
            totalCalls += p.getRequestCount();
        }

        for (Process p : sequence.getProcesses()) {
            double frac = p.getRequestCount() / (double)totalCalls;
            int frameCount = (int) Math.round(Utils.TOTAL_FRAME_COUNT * frac);

            if (frameCount == 0) frameCount = 1;

            for (int i = 0; i < frameCount; i++) {
                appendFrame(p);
            }
        }

        super.startSimulation(sequence);
    }
}
