package cz.swisz.so.task3.algorithms;

import cz.swisz.so.task3.PageReplacementAlgorithm;
import cz.swisz.so.task3.Utils;

import java.util.Random;

public class RAND extends PageReplacementAlgorithm {
    private final Random _rand;

    public RAND() {
        _rand = new Random();
    }

    @Override
    protected void requestSwap(int pageToBeSwapped) {
        int frame = _rand.nextInt(Utils.FRAME_COUNT);
        swapPages(frame, pageToBeSwapped);
    }
}
