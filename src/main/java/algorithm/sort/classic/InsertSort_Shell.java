package algorithm.sort.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 插入排序(3)-->希尔排序
 * <pre/>
 *
 * 基本思想：
 * 本质上是分组插入排序，把记录按增量分组，对每组采用直接插入排序，随着增量逐渐减小，
 * 所分成的组包含的记录越来越多，当增量的值减小到1时，
 * 整个数据合成为一组，构成一组有序记录，则完成排序。
 *
 * ..L.....j.....M.........R i
 * -----------------------------------
 * | | | | | | | | | | | | | | | | | |
 * -----------------------------------
 *
 * 算法性能分析：
 * 平均时间复杂度：
 * 希尔排序的时间复杂度和其增量序列有关系，这涉及到数学上尚未解决的难题；
 * 不过在某些序列中复杂度可以为O(n^1.3);
 */
public class InsertSort_Shell {
    private static final Logger LOG = LoggerFactory.getLogger(InsertSort_Shell.class);

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void descendingSort(int[] arr) {
        int gap = arr.length / 2;
        while (gap >= 1) {
            for (int i = gap; i < arr.length; i++) {
                int j;
                int temp = arr[i];
                for (j = i - gap; j >= 0 && temp > arr[j]; j = j - gap) {
                    arr[j + gap] = arr[j];
                }
                arr[j + gap] = temp;
            }
            gap = gap / 2;
        }
    }

    @Deprecated
    public static void main(String[] args) {
        int[] arr = new int[]{57, 68, 59, 52, 44, 49, 29, 37, 8, 16};
        descendingSort(arr);
        LOG.info("descendingArr={}", arr);
    }

}
