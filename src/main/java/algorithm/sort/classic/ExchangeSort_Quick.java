package algorithm.sort.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 交换排序(2)-->快速排序
 * <pre/>
 *
 * 基本思想：
 * 选择一个基准元素，通常选择第一个元素或者最后一个元素，通过一趟扫描，
 * 将待排序列分成两部分，一部分比基准元素小，一部分大于等于基准元素，
 * 此时基准元素在其排好序后的正确位置，然后再用同样的方法递归地排序划分的两部分。
 *
 * ........j i
 * -------------------------
 * | | | | | | | | | | | | |
 * -------------------------
 *
 * 算法性能分析：
 * 快速排序是不稳定的排序。
 * 快速排序的时间复杂度为O(nlogn)。
 * 当n较大时使用快排比较好，当序列基本有序时用快排反而不好。
 */
public class ExchangeSort_Quick {
    private static final Logger LOG = LoggerFactory.getLogger(ExchangeSort_Quick.class);

    public static void descendingSort(int[] arr, int idxLow, int idxHigh) {
        if (idxLow < idxHigh) {
            //选第一个数作为基准
            int pivot = arr[idxLow];
            int left = idxLow;
            int right = idxHigh;
            while (left < right) {
                while (left < right && arr[right] > pivot) {
                    right--;
                }
                arr[left] = arr[right];
                while (left < right && arr[left] < pivot) {
                    left++;
                }
                arr[right] = arr[left];
            }
            arr[left] = pivot;
            descendingSort(arr, idxLow, left - 1);
            descendingSort(arr, right + 1, idxHigh);
        }
    }

    @Deprecated
    public static void main(String[] args) {
        int[] arr = new int[]{57, 68, 59, 52, 44, 49, 29, 37, 8, 16};
        descendingSort(arr, 0, 9);
        LOG.info("descendingArr={}", arr);
    }

}
