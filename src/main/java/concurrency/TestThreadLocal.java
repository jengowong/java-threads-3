package concurrency;

/**
 * {@link TestThreadLocal}
 */
public class TestThreadLocal {

    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();

        Thread thread1 = new Thread(myRunnable, "t1");
        Thread thread2 = new Thread(myRunnable, "t2");

        thread1.start();
        thread2.start();
    }

    public static class MyRunnable implements Runnable {
        private ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

        @Override
        public void run() {
            threadLocal.set((int) (Math.random() * 100D));
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
        }
    }

}
