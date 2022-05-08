package cz.swisz.so.task4;

interface TestI {
    void run();
}

public class Test {
    private static class Inner {
        Inner() {
            System.out.println("AAA");
        }
    }

    private static void run(TestI test) {
        test.run();
    }

    public static void main(String[] args) {
        run(Inner::new);
    }
}
