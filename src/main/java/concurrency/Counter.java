package concurrency;

/**
 * {@link Counter}
 */
public class Counter {

    public synchronized void add(long value) throws Exception {
        System.out.println("in add " + value);
        while (true) {
            value++;
        }
    }

    public synchronized void subtract(long value) throws Exception {
        System.out.println("in subtract " + value);
        Thread.currentThread().sleep(500);
    }

}
