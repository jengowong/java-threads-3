package concurrency.threadlocal;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link TestThreadLocal}
 */
@Slf4j
public class TestThreadLocal {

    private static ThreadLocal<String> myThreadLocal = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return "This is the initial value";
        }
    };

    public static class MyRunnable implements Runnable {

        private ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

        @Override
        public void run() {
            threadLocal.set((int) (Math.random() * 100D));

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error("异常", e);
            }

            log.info("{}", threadLocal.get());
        }
    }

    public static void main(String[] args) throws Exception {
        log.info("{}", myThreadLocal.get());
        myThreadLocal.set("aaa");
        log.info("{}", myThreadLocal.get());

        MyRunnable sharedRunnableInstance = new MyRunnable();

        Thread thread1 = new Thread(sharedRunnableInstance);
        Thread thread2 = new Thread(sharedRunnableInstance);

        thread1.start();
        thread2.start();

        thread1.join(); //wait for thread 1 to terminate
        thread2.join(); //wait for thread 2 to terminate
    }

}
