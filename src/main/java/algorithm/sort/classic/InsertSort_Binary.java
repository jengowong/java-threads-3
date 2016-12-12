package algorithm.sort.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 插入排序(2)-->二分法插入排序
 * <pre/>
 *
 * 基本思想：
 * 在直接插入排序的基础上，为了减少比较次数，快速定位到插入位置，可以使用二分查找。
 *
 * ..L.....j.....M.........R i
 * -----------------------------------
 * | | | | | | | | | | | | | | | | | |
 * -----------------------------------
 *
 * 算法性能分析：
 * 二分插入排序的比较次数与待排序记录的初始状态无关，仅依赖于记录的个数。
 * 当n较大时，比直接插入排序的最大比较次数少得多。
 * 但大于直接插入排序的最小比较次数。
 * 算法的移动次数与直接插入排序算法的相同，
 * 最坏的情况为n^2/2，最好的情况为n，平均移动次数为O(n^2)。
 */
public class InsertSort_Binary {
    private static final Logger LOG = LoggerFactory.getLogger(InsertSort_Binary.class);

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void descendingSort(int[] arr) {
        int l, r, m;

        for (int i = 1; i < arr.length; i++) { //待排序的数
            l = 0;
            r = i - 1;
            //二分查找到插入的位置
            while (l <= r) {
                m = (r + l) / 2;
                if (arr[m] < arr[i]) {         //比较次数
                    r = m - 1;
                } else {
                    l = m + 1;
                }
            }
            for (int j = i - 1; j >= l; j--) { //已经排好序的队列
                swap(arr, j, j + 1);           //交换次数，用到了每一次的比较
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
