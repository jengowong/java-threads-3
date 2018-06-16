package concurrent.test;

/**
 * {@link TestSynchronized}
 *
 * https://blog.csdn.net/isresultxal/article/details/53286698
 */
public class TestSynchronized {

    private String lock = "lock";

    private void method() {
        synchronized (lock) {
            try {
                System.out.println(Thread.currentThread().getName() + " begin " + lock);
                lock = "lock modify";
                System.out.println(Thread.currentThread().getName() + " end " + lock);
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        final TestSynchronized testSynchronized = new TestSynchronized();

        Thread threadA = new Thread() {
            @Override
            public void run() {
                testSynchronized.method();
            }
        };
        threadA.setName("A");
        threadA.start();

        Thread threadB = new Thread() {
            @Override
            public void run() {
                testSynchronized.method();
            }
        };
        threadB.setName("B");
        //Thread.sleep(50);
        threadB.start();
    }

}
