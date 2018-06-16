package priorityqueue;

import java.util.Comparator;

/**
 * {@link HeapTopN} 堆数据结构应用实例
 *
 * topn指的是从已经存在的序列中，找出最大(或最小)的前n个元素。
 * <pre>
 * topn算法实现思路(找最大的n个元素)
 *   1. 取出数组的前n个元素，创建长度为n的最小堆。
 *   2. 从n开始循环数组的剩余元素，如果元素(a)比最小堆的根节点大，将a设置成最小堆的根节点，并让堆保持最小堆的特性。
 *   3. 循环完成后，最小堆中的所有元素就是需要找的最大的n个元素。
 */
public class HeapTopN {

    public static int[] toPrimitive(Integer[] a) {
        if (a == null) {
            return null;
        }
        if (a.length == 0) {
            return new int[0];
        }
        int result[] = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i];
        }
        return result;
    }

    /**
     * 计算topN大
     *
     * @param a array
     * @param n top n
     *
     * @return [top n]
     */
    public static int[] topn(int[] a, int n) {
        if (n >= a.length) {
            return a;
        }
        Integer[] topn = new Integer[n];
        for (int i = 0; i < n; i++) {
            topn[i] = a[i];
        }
        Heap<Integer> heap = new Heap<>(topn,
                new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o1 - o2;
                    }
                });
        for (int i = n; i < a.length; i++) {
            int value = a[i];
            int min = heap.root();
            if (value > min) {
                heap.setRoot(value);
            }
        }
        heap.sort();
        return toPrimitive(topn);
    }


    @Deprecated
    public static void main(String[] args) {
        int[] array = new int[]{101, 2, 3, 4, 5, 6, 7, 8, 11, 9, 10, 100};
        int[] result = topn(array, 5);
        for (int i : result) {
            System.out.print(i + ",");
        }
    }

}
