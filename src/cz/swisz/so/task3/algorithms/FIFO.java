package cz.swisz.so.task3.algorithms;

import cz.swisz.so.task3.PageReplacementAlgorithm;
import cz.swisz.so.task3.Process;
import cz.swisz.so.task3.Utils;

import java.util.LinkedList;
import java.util.Queue;

public class FIFO extends PageReplacementAlgorithm {
    private Queue<Integer> _q;

    public FIFO() {
        _q = null;
    }

    @Override
    protected void requestSwap(int pageToBeSwapped) {
        if (_q == null) {
            // That's the first swap, get information about current memory contents

            _q = new LinkedList<>();
            for (int i = 0; i < Utils.FRAME_COUNT; i++) {
                _q.add(i);
            }
        }

        assert (!_q.isEmpty());

        int frame = _q.poll();
        swapPages(frame, pageToBeSwapped);
        _q.add(frame);
    }
}
