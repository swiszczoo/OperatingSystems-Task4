package cz.swisz.so.task4;

import java.util.Random;

public final class Generator {
    private static final Random _rand = new Random();
    private static final int OP_MAX_LENGTH = 2048;

    public static Process generateProcess(int iterations) {
        Process proc = new Process(_rand.nextInt(30) + 20);
        int maxAddr = proc.getPageCount() * Utils.PAGE_SIZE;

        for (int i = 0; i < iterations; i++) {
            int opType = _rand.nextInt(4);

            switch (opType) {
                case 0 -> appendIterationBlock(proc, maxAddr);
                case 1 -> appendCopyBlock(proc, maxAddr);
                case 2 -> appendRandomAccessBlock(proc, maxAddr);
                case 3 -> appendWaitBlock(proc, maxAddr);
            }
        }

        return proc;
    }

    private static void appendIterationBlock(Process proc, int maxAddress) {
        int length = _rand.nextInt(OP_MAX_LENGTH) + 1;
        int from = _rand.nextInt(maxAddress - length);

        for (int i = from; i < from + length; i++) {
            proc.addRequest(new Process.Request(0, i));
        }
    }

    private static void appendCopyBlock(Process proc, int maxAddress) {
        int length = _rand.nextInt(OP_MAX_LENGTH) + 1;
        int from = _rand.nextInt(maxAddress - length);
        int to = _rand.nextInt(maxAddress - length);

        for (int i = 0; i < length; i++) {
            proc.addRequest(new Process.Request(0, from + i));
            proc.addRequest(new Process.Request(0, to + i));
        }
    }

    private static void appendRandomAccessBlock(Process proc, int maxAddress) {
        int length = _rand.nextInt(OP_MAX_LENGTH) + 1;
        int blockSize = _rand.nextInt(OP_MAX_LENGTH) + 1;
        int from = _rand.nextInt(maxAddress - blockSize);

        for (int i = 0; i < length; i++) {
            proc.addRequest(new Process.Request(0, from + _rand.nextInt(blockSize)));
        }
    }

    private static void appendWaitBlock(Process proc, int maxAddress) {

    }
}
