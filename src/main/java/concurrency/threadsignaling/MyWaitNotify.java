package concurrency.threadsignaling;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link MyWaitNotify}
 */
@Slf4j
public class MyWaitNotify {

    private MonitorObject myMonitorObject = new MonitorObject(); //Don't call wait() on constant String's or global objects
    private boolean wasSignalled = false;

    public void doWait() {
        synchronized (myMonitorObject) {
            while (!wasSignalled) {
                try {
                    myMonitorObject.wait();
                } catch (Exception e) {
                    log.error("异常", e);
                }
            }
            //clear signal and continue running.
            wasSignalled = false;
        }
    }

    public void doNotify() {
        synchronized (myMonitorObject) {
            wasSignalled = true;
            myMonitorObject.notify();
        }
    }

    public static void main(String[] args) throws Exception {
        MyWaitNotify myWaitNotify = new MyWaitNotify();

        Thread t1 = new Thread() {
            @Override
            public void run() {
                log.info("{} before wait", Thread.currentThread().getName());
                myWaitNotify.doWait();
                log.info("{} after wait", Thread.currentThread().getName());
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                log.info("{} before notify", Thread.currentThread().getName());
                myWaitNotify.doNotify();
                log.info("{} after notify", Thread.currentThread().getName());
            }
        };

        t1.start();
        Thread.sleep(1000);
        t2.start();
    }

}
