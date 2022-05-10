package cz.swisz.so.task4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;

public abstract class PageReplacementAlgorithm {
    private class ProcessData {
        private final ArrayList<Integer> pageAssignment;
        private final BitSet pagesInRam;
        private int i;

        private ProcessData(Process proc) {
            pageAssignment = new ArrayList<>();
            pagesInRam = new BitSet(proc.getPageCount());
            pagesInRam.clear();
            i = 0;
        }
    }

    private final HashMap<Process, ProcessData> _procData;
    private int _totalAssignedFrames;

    public PageReplacementAlgorithm() {
        _procData = new HashMap<>();
        _totalAssignedFrames = 0;
    }

    public void runSimulation(Sequence sequence) {
        for (Process p : sequence.getProcesses()) {
            _procData.put(p, new ProcessData(p));
        }

        startSimulation(sequence);

        int reqCount = sequence.getRequestCount();
        for (int i = 0; i < reqCount; i++) {
            Sequence.Request request = sequence.getRequestAt(i);
            ProcessData data = _procData.get(request.getProcess());
            data.i++;
            int page = request.getPage();

            requestPage(request.getProcess(), page);

            if (data.pagesInRam.get(page)) {
                continue;
            }

            boolean foundEmptySpot = false;
            for (int j = 0; j < data.pageAssignment.size(); j++) {
                if (data.pageAssignment.get(j) == -1) {
                    data.pageAssignment.set(j, page);
                    data.pagesInRam.set(page);
                    request.getProcess().increasePageErrors();
                    foundEmptySpot = true;
                    break;
                }
            }

            if (foundEmptySpot) continue;

            requestSwap(request.getProcess(), page);
            request.getProcess().increasePageErrors();

            assert(isPageInRam(request.getProcess(), page));
        }
    }

    protected int getPageAtFrame(Process proc, int index) {
        return _procData.get(proc).pageAssignment.get(index);
    }

    protected int swapPages(Process proc, int frame, int newPage) {
        int oldPage = _procData.get(proc).pageAssignment.get(frame);
        _procData.get(proc).pagesInRam.clear(oldPage);
        _procData.get(proc).pageAssignment.set(frame, newPage);
        _procData.get(proc).pagesInRam.set(newPage);

        return oldPage;
    }

    protected int getCurrentIteration(Process proc) {
        return _procData.get(proc).i;
    }

    protected boolean isPageInRam(Process proc, int page) {
        return _procData.get(proc).pagesInRam.get(page);
    }

    protected int findCorrespondingFrame(Process proc, int page) {
        ProcessData dat = _procData.get(proc);
        for (int i = 0; i < dat.pageAssignment.size(); i++) {
            if (dat.pageAssignment.get(i) == page)
                return i;
        }

        return -1;
    }

    protected void appendFrame(Process proc) {
        if (_totalAssignedFrames < Utils.TOTAL_FRAME_COUNT) {
            _procData.get(proc).pageAssignment.add(-1);
            _totalAssignedFrames++;
        }
    }

    protected void discardFrame(Process proc) {
        ProcessData data = _procData.get(proc);
        if (data.pageAssignment.size() > 1) {
            int page = data.pageAssignment.get(data.pageAssignment.size() - 1);
            data.pageAssignment.remove(data.pageAssignment.size() - 1);
            if (page >= 0) data.pagesInRam.clear(page);
            _totalAssignedFrames--;
        }
    }

    protected int getFrameCount(Process proc) {
        return _procData.get(proc).pageAssignment.size();
    }

    public int getFramesForProcess(Process proc) {
        return _procData.get(proc).pageAssignment.size();
    }

    protected void requestPage(Process proc, int page) {}
    protected void startSimulation(Sequence sequence) {}

    protected abstract void requestSwap(Process proc, int pageToBeSwapped);
}
