package cz.swisz.so.task4.algorithms;

import cz.swisz.so.task4.Process;
import cz.swisz.so.task4.Sequence;

import java.util.*;

public class WorkingSet extends LRU {
    private final HashMap<Process, LinkedList<Integer>> m_workingSet;
    private static final int DELTA = 80;

    public WorkingSet() {
        m_workingSet = new HashMap<>();
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
    protected void requestPage(Process proc, int page) {
        super.requestPage(proc, page);

        if (!m_workingSet.containsKey(proc)) {
            m_workingSet.put(proc, new LinkedList<>());
        }

        LinkedList<Integer> requestedPages = m_workingSet.get(proc);
        if (requestedPages.isEmpty() || page != requestedPages.getLast()) {
            requestedPages.add(page);
        }
        if (requestedPages.size() > DELTA) {
            requestedPages.removeFirst();
        }

        Set<Integer> uniquePages = new HashSet<>(requestedPages);
        if (uniquePages.size() > getFrameCount(proc)) {
            appendFrame(proc);
        } else if (uniquePages.size() < getFrameCount(proc)) {
            discardFrame(proc);
        }
    }
}
