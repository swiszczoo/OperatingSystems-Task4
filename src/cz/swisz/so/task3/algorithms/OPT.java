package cz.swisz.so.task3.algorithms;

import cz.swisz.so.task3.PageReplacementAlgorithm;
import cz.swisz.so.task3.Process;
import cz.swisz.so.task3.Utils;

import java.util.LinkedList;
import java.util.Queue;

public class OPT extends PageReplacementAlgorithm {
    private Queue<Integer>[] _qs;

    public OPT() {
        _qs = null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void startSimulation(Process process) {
        _qs = (Queue<Integer>[])new Queue[Utils.PAGE_COUNT];
        for (int i = 0; i < _qs.length; i++) {
            _qs[i] = new LinkedList<>();
        }

        int reqCount = process.getRequestCount();
        for (int i = 0; i < reqCount; i++) {
            Process.Request req = process.getRequestAt(i);
            _qs[req.getPage()].add(i);
        }
    }

    @Override
    protected void requestSwap(int pageToBeSwapped) {
        int iter = getCurrentIteration();

        for (Queue<Integer> q : _qs) {
            while (!q.isEmpty() && q.peek() <= iter) {
                q.poll();
            }
        }

        // Find the loaded page that will be used at the latest

        int maxIter = iter;
        int maxPage = pageToBeSwapped;

        for (int i = 0; i < _qs.length; i++) {
            Queue<Integer> q = _qs[i];

            if (!isPageInRam(i)) continue;

            if (q.isEmpty()) {
                maxPage = i;
                break;
            }

            int nextIter = q.peek();
            if (nextIter > maxIter) {
                maxIter = nextIter;
                maxPage = i;
            }
        }

        int frame = findCorrespondingFrame(maxPage);
        assert(frame >= 0);

        swapPages(frame, pageToBeSwapped);
    }
}
