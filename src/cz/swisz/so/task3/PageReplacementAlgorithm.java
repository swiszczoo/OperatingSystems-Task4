package cz.swisz.so.task3;

import java.util.Arrays;
import java.util.BitSet;

public abstract class PageReplacementAlgorithm {
    private final int[] _pageAssignment;
    private final BitSet _pagesInRam;
    private int _emptyFramesLeft;
    private int _i;

    public PageReplacementAlgorithm() {
        _pageAssignment = new int[Utils.FRAME_COUNT];
        Arrays.fill(_pageAssignment, -1);

        _emptyFramesLeft = Utils.FRAME_COUNT;

        _pagesInRam = new BitSet(Utils.PAGE_COUNT);
        _pagesInRam.clear();

        _i = -1;
    }

    public void runSimulation(Process process) {
        _i = -1;
        startSimulation(process);

        int reqCount = process.getRequestCount();
        for (int i = 0; i < reqCount; i++) {
            _i = i;
            Process.Request request = process.getRequestAt(i);
            int page = request.getPage();

            requestPage(page);

            if (_pagesInRam.get(page)) {
                continue;
            }

            if (_emptyFramesLeft > 0) {
                _pageAssignment[Utils.FRAME_COUNT - _emptyFramesLeft] = page;
                _pagesInRam.set(page);
                _emptyFramesLeft--;
                continue;
            }

            requestSwap(page);
            process.increasePageErrors();

            assert(isPageInRam(page));
        }
    }

    protected int getPageAtFrame(int index) {
        return _pageAssignment[index];
    }

    protected int swapPages(int frame, int newPage) {
        int oldPage = _pageAssignment[frame];
        _pagesInRam.clear(oldPage);
        _pageAssignment[frame] = newPage;
        _pagesInRam.set(newPage);

        return oldPage;
    }

    protected int getCurrentIteration() {
        return _i;
    }

    protected boolean isPageInRam(int page) {
        return _pagesInRam.get(page);
    }

    protected int findCorrespondingFrame(int page) {
        for (int i = 0; i < Utils.FRAME_COUNT; i++) {
            if (_pageAssignment[i] == page)
                return i;
        }

        return -1;
    }

    protected void requestPage(int page) {}
    protected void startSimulation(Process process) {}

    protected abstract void requestSwap(int pageToBeSwapped);
}
