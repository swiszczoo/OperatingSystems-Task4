package cz.swisz.so.task3;

import java.util.Random;

public final class Generator {
    private static final Random _rand = new Random();
    private static final int OP_MAX_LENGTH = 4096;

    public static Process generateProcess(int iterations) {
        Process proc = new Process();

        for (int i = 0; i < iterations; i++) {
            int opType = _rand.nextInt(4);

            switch (opType) {
                case 0:
                    appendIterationBlock(proc);
                    break;
                case 1:
                    appendCopyBlock(proc);
                    break;
                case 2:
                    appendRandomAccessBlock(proc);
                    break;
                case 3:
                    appendWaitBlock(proc);
                    break;
            }
        }

        return proc;
    }

    private static void appendIterationBlock(Process proc) {
        int length = _rand.nextInt(OP_MAX_LENGTH);
        int from = _rand.nextInt(Utils.MAX_ADDRESS - length);

        for (int i = from; i < from + length; i++) {
            proc.addRequest(new Process.Request(0, i));
        }
    }

    private static void appendCopyBlock(Process proc) {
        int length = _rand.nextInt(OP_MAX_LENGTH);
        int from = _rand.nextInt(Utils.MAX_ADDRESS - length);
        int to = _rand.nextInt(Utils.MAX_ADDRESS - length);

        for (int i = 0; i < length; i++) {
            proc.addRequest(new Process.Request(0, from + i));
            proc.addRequest(new Process.Request(0, to + i));
        }
    }

    private static void appendRandomAccessBlock(Process proc) {
        int length = _rand.nextInt(OP_MAX_LENGTH);
        int blockSize = _rand.nextInt(OP_MAX_LENGTH);
        int from = _rand.nextInt(Utils.MAX_ADDRESS - blockSize);

        for (int i = 0; i < length; i++) {
            proc.addRequest(new Process.Request(0, from + _rand.nextInt(blockSize)));
        }
    }

    private static void appendWaitBlock(Process proc) {

    }
}
