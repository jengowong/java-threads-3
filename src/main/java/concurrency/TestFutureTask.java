package concurrency;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * {@link TestFutureTask}
 *
 * https://mp.weixin.qq.com/s/ak5TXlYpdzcCWynb-5L19Q
 */
public class TestFutureTask {

    String t1Value = StringUtils.EMPTY;
    String t2Value = StringUtils.EMPTY;


    public static void main(String[] args) throws Exception {
        TestFutureTask obj = new TestFutureTask();

        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                obj.t1Value = "t1Value";
                System.out.println("thread_1 run method");
            }
        };
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                obj.t2Value = "t2Value";
                System.out.println("thread_2 runnable method");
            }
        }, "t2");
        t2.start();


        System.out.println("main thread");
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("t1Value=" + obj.t1Value);
        System.out.println("t2Value=" + obj.t2Value);


        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("正在下载中...");
                TimeUnit.SECONDS.sleep(3);
                return "Hello World!";
            }
        };
        FutureTask<String> futureTask = new FutureTask<String>(callable);
        new Thread(futureTask, "t3").start();
        System.out.println("我们做点其它什么吧...");
        System.out.println("从网络上下载的结果为:" + futureTask.get());
        System.out.println("Finish!");


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("正在下载中...");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        FutureTask<String> runnableTask = new FutureTask<String>(runnable, "我是返回的结果");
        new Thread(runnableTask, "t4").start();
        System.out.println("我们做点其它什么吧...");
        System.out.println("从网络上下载的结果为:" + runnableTask.get());
        System.out.println("Finish!");
    }

}
