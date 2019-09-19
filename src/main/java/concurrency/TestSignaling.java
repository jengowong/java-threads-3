package concurrency;

/**
 * {@link TestSignaling}
 *
 * http://tutorials.jenkov.com/java-concurrency/thread-signaling.html
 */
public class TestSignaling {

    public static void main(String[] args) {
        Monitor monitor = new Monitor();

        Thread t1 = new DoWaitThread(monitor);
        Thread t2 = new DoNotifyThread(monitor);

        t1.start();
        t2.start();
    }

    static class Monitor {
        public synchronized void doWait() throws Exception {
            this.wait();
        }

        public synchronized void doNotify() throws Exception {
            this.notify();
        }
    }

    static class DoWaitThread extends Thread {
        Monitor monitor;

        public DoWaitThread(Monitor monitor) {
            this.monitor = monitor;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + "before doWait");
                monitor.doWait();
                System.out.println(Thread.currentThread().getName() + "after doWait");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class DoNotifyThread extends Thread {
        Monitor monitor;

        public DoNotifyThread(Monitor monitor) {
            this.monitor = monitor;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + "before doNotify");
                monitor.doNotify();
                System.out.println(Thread.currentThread().getName() + "after doNotify");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
