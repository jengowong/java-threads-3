package concurrency;

/**
 * {@link TestSynchronized}
 */
public class TestSynchronized {

    public static void main(String[] args) throws Exception {
        Counter counter = new Counter();
        Thread threadA = new CounterAddThread(counter);
        Thread threadB = new CounterSubtractThread(counter);
        threadA.start();
        threadB.start();
    }

}
