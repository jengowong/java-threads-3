package algorithm.sort.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 归并排序
 * <pre/>
 *
 * 基本思想：
 * 分而治之，合并为一，将多个有序子数组合并为一个新的有序的数组。
 *
 * ........j i
 * -------------------------
 * | | | | | | | | | | | | |
 * -------------------------
 *
 * 算法性能分析：
 * 归并排序是稳定的排序方法,时间复杂度为O(nlogn)。
 * 速度仅次于快速排序，一般用于对总体无序，但是各子项相对有序的数列。
 */
public class MergeSort {
    private static final Logger LOG = LoggerFactory.getLogger(MergeSort.class);

    public static int[] descendingSort(int[] arr, int low, int high) {
        if (low < high) {
            //选第一个数作为基准
            int pivot = arr[low];
            int left = low;
            int right = high;
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
            descendingSort(arr, low, left - 1);
            descendingSort(arr, right + 1, high);
        }
        return arr;
    }

    @Deprecated
    public static void main(String[] args) {
        int[] unsortedArr = new int[]{57, 68, 59, 52, 44, 49, 29, 37, 8, 16};
        int[] sortedArr = descendingSort(unsortedArr, 0, 9);
        LOG.info("descendingArr={}", sortedArr);
    }

}
