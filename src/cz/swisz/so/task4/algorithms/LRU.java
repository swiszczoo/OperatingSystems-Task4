package cz.swisz.so.task4.algorithms;

import cz.swisz.so.task4.PageReplacementAlgorithm;
import cz.swisz.so.task4.Process;
import cz.swisz.so.task4.Utils;

import java.util.HashMap;

public class LRU extends PageReplacementAlgorithm {
    private final HashMap<Process, int[]> _lastUsed;

    public LRU() {
        _lastUsed = new HashMap<>();
    }

    @Override
    protected void requestPage(Process proc, int page) {
        if (!_lastUsed.containsKey(proc)) {
            _lastUsed.put(proc, new int[proc.getPageCount()]);
        }

        _lastUsed.get(proc)[page] = getCurrentIteration(proc);
    }

    @Override
    protected void requestSwap(Process proc, int pageToBeSwapped) {
        int minUsed = getCurrentIteration(proc);
        int minPage = 0;
        int[] lastUsed = _lastUsed.get(proc);

        for (int i = 0; i < lastUsed.length; i++) {
            if (lastUsed[i] < minUsed && isPageInRam(proc, i)) {
                minUsed = lastUsed[i];
                minPage = i;
            }
        }

        int frame = findCorrespondingFrame(proc, minPage);
        assert(frame >= 0);
        swapPages(proc, frame, pageToBeSwapped);
    }
}
