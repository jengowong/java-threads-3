package algorithm.priorityqueue;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

/**
 * {@link PriorityQueueExample}
 * <pre>
 * PriorityQueue是一种基于优先级堆的无限队列，即优先级队列。
 * 优先级队列是不同于先进先出队列的另一种队列。每次从队列中取出的是具有最高优先权的元素。
 *
 * 注意1: 该队列是用数组实现，但是数组大小可以动态增加，容量无限。
 * 注意2: 此实现不是同步的。不是线程安全的。线程安全请用PriorityBlockingQueue类。
 * 注意3: 不允许使用 null 元素。
 * 注意4: 此实现为插入方法（offer、poll、remove() 和 add 方法）提供 O(log(n)) 时间；
 *       为 remove(Object) 和 contains(Object) 方法提供线性时间；
 *       为检索方法（peek、element 和 size）提供固定时间。
 * 注意5: 方法iterator()中提供的迭代器并不保证以有序的方式遍历优先级队列中的元素。
 * 注意6: 可以在构造函数中指定如何排序。
 * 注意7: 此类及其迭代器实现了 Collection 和 Iterator 接口的所有可选 方法。
 */
public class PriorityQueueExample {

    public static void main(String[] args) {
        //natural ordering example of priority queue
        Queue<Integer> integerPriorityQueue = new PriorityQueue<>(7);
        Random rand = new Random();
        for (int i = 0; i < 7; i++) {
            integerPriorityQueue.add(rand.nextInt(100));
        }
        for (int i = 0; i < 7; i++) {
            Integer in = integerPriorityQueue.poll();
            System.out.println("Processing Integer:" + in);
        }

        //PriorityQueue example with Comparator
        Queue<Customer> customerPriorityQueue = new PriorityQueue<>(7, idComparator);
        addDataToQueue(customerPriorityQueue);
        pollDataFromQueue(customerPriorityQueue);
    }

    //Comparator anonymous class implementation
    private static Comparator<Customer> idComparator =
            new Comparator<Customer>() {
                @Override
                public int compare(Customer c1, Customer c2) {
                    return c1.getId() - c2.getId();
                }
            };

    //utility method to add random data to Queue
    private static void addDataToQueue(Queue<Customer> customerPriorityQueue) {
        Random rand = new Random();
        for (int i = 0; i < 7; i++) {
            int id = rand.nextInt(100);
            customerPriorityQueue.add(new Customer(id, "Pankaj" + id));
        }
    }

    //utility method to poll data from queue
    private static void pollDataFromQueue(Queue<Customer> customerPriorityQueue) {
        while (true) {
            Customer customer = customerPriorityQueue.poll();
            if (customer == null) {
                break;
            }
            System.out.println("Processing Customer with ID=" + customer.getId());
        }
    }

}
