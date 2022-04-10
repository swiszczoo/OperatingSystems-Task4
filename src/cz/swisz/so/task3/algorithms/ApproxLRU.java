package cz.swisz.so.task3.algorithms;

import cz.swisz.so.task3.PageReplacementAlgorithm;
import cz.swisz.so.task3.Utils;

import java.util.BitSet;
import java.util.LinkedList;
import java.util.Queue;

public class ApproxLRU extends PageReplacementAlgorithm {
    private Queue<Integer> _q;
    private BitSet _secondChance;

    public ApproxLRU() {
        _q = null;
        _secondChance = new BitSet(Utils.PAGE_COUNT);
        _secondChance.clear();
    }

    @Override
    protected void requestPage(int page) {
        _secondChance.set(page);
    }

    @Override
    protected void requestSwap(int pageToBeSwapped) {
        if (_q == null) {
            // That's the first swap, get information about current memory contents

            _q = new LinkedList<>();
            for (int i = 0; i < Utils.FRAME_COUNT; i++) {
                _q.add(getPageAtFrame(i));
                _secondChance.set(getPageAtFrame(i));
            }
        }

        while (!_q.isEmpty()) {
            int page = _q.poll();

            if (_secondChance.get(page)) {
                _secondChance.clear(page);
                _q.add(page);
            } else {
                int frame = findCorrespondingFrame(page);
                assert (frame >= 0);
                swapPages(frame, pageToBeSwapped);
                _q.add(pageToBeSwapped);
                _secondChance.set(pageToBeSwapped);
                break;
            }
        }

        assert(false);
    }
}
