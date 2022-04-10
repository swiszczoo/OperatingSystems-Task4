package cz.swisz.so.task3.algorithms;

import cz.swisz.so.task3.PageReplacementAlgorithm;
import cz.swisz.so.task3.Utils;

public class LRU extends PageReplacementAlgorithm {
    private final int[] _lastUsed;

    public LRU() {
        _lastUsed = new int[Utils.PAGE_COUNT];
    }

    @Override
    protected void requestPage(int page) {
        _lastUsed[page] = getCurrentIteration();
    }

    @Override
    protected void requestSwap(int pageToBeSwapped) {
        int minUsed = getCurrentIteration();
        int minPage = 0;

        for (int i = 0; i < _lastUsed.length; i++) {
            if (_lastUsed[i] < minUsed && isPageInRam(i)) {
                minUsed = _lastUsed[i];
                minPage = i;
            }
        }

        int frame = findCorrespondingFrame(minPage);
        assert(frame >= 0);
        swapPages(frame, pageToBeSwapped);
    }
}
