package cz.swisz.so.task4;

public final class Utils {
    public static final int PAGE_COUNT = 48;
    public static final int PAGE_SIZE = 256;
    public static final int MAX_ADDRESS = PAGE_COUNT * PAGE_SIZE;
    public static final int FRAME_COUNT = 16;
    public static final int TOTAL_FRAME_COUNT = 150;

    public static int convertAddressToPage(int address)
    {
        return address / PAGE_SIZE;
    }
}
