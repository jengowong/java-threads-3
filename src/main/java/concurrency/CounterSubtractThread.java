package concurrency;

/**
 * {@link CounterSubtractThread}
 */
public class CounterSubtractThread extends Thread {

    private Counter counter;

    public CounterSubtractThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                counter.subtract(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
