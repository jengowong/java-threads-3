package algorithm.sort.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 插入排序(1)-->直接插入排序
 * <pre/>
 *
 * 基本思想：
 * 将一个待排序的数插入到一个已经排好序的队列中，找一个合适的位置插入。
 * 从后向前找到合适的位置，直到全部插入排序为止。
 *
 * i --->
 * <--- j
 * ........j i
 * -------------------------
 * | | | | | | | | | | | | |
 * -------------------------
 *
 * 算法性能分析：
 * 数组初始态不同，直接插入排序所耗费的时间有很大差异。
 * 如果按序排列好的数组，则每个插入排序的数只需要比较一次即可，时间复杂度O(n)，
 * 如果是反序的数组，那么每个待排序的数都要比较i-1次，故时间复杂度为O(n^2)。
 *
 * 1 + 2 + 3 + ... + (n-1) = (n-1)n/2 = (n^2 - n)/2
 */
public class InsertSort_Straight {
    private static final Logger LOG = LoggerFactory.getLogger(InsertSort_Straight.class);

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void descendingSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) { //待排序的数

            for (int j = i - 1; j >= 0; j--) { //已经排好序的队列

                if (arr[j] < arr[j + 1]) {     //比较次数，有点比较不发生交换
                    swap(arr, j, j + 1);       //交换次数
                } else {
                    break;
                }
            }
        }
    }

    @Deprecated
    public static void main(String[] args) {
        int[] arr = new int[]{57, 68, 59, 52, 44, 49, 29, 37, 8, 16};
        descendingSort(arr);
        LOG.info("descendingArr={}", arr);
    }

}
