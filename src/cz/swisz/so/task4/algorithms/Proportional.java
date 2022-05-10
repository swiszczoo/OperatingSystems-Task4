package cz.swisz.so.task4.algorithms;

import cz.swisz.so.task4.Process;
import cz.swisz.so.task4.Sequence;
import cz.swisz.so.task4.Utils;

public class Proportional extends LRU {
    @Override
    protected void startSimulation(Sequence sequence) {
        long totalFrames = 0;
        for (Process p : sequence.getProcesses()) {
            totalFrames += p.getPageCount();
        }

        for (Process p : sequence.getProcesses()) {
            double frac = p.getPageCount() / (double)totalFrames;
            int frameCount = (int) Math.round(Utils.TOTAL_FRAME_COUNT * frac);

            if (frameCount == 0) frameCount = 1;

            for (int i = 0; i < frameCount; i++) {
                appendFrame(p);
            }
        }

        super.startSimulation(sequence);
    }
}
