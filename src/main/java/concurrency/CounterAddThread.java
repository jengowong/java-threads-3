package concurrency;

/**
 * {@link CounterAddThread}
 */
public class CounterAddThread extends Thread {
    private Counter counter;

    public CounterAddThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                counter.add(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
