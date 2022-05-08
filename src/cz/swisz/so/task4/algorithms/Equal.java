package cz.swisz.so.task4.algorithms;

import cz.swisz.so.task4.Process;
import cz.swisz.so.task4.Sequence;
import cz.swisz.so.task4.Utils;

public class Equal extends LRU {
    @Override
    protected void startSimulation(Sequence sequence) {
        int perProcess = Utils.TOTAL_FRAME_COUNT / sequence.getProcesses().size();

        for (Process p : sequence.getProcesses()) {
            for (int i = 0; i < perProcess; i++) {
                appendFrame(p);
            }
        }

        super.startSimulation(sequence);
    }
}
