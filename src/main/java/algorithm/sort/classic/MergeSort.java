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
 * 实现思路：
 * 可以将数组分成二组，依次类推，当分出来的小组只有一个数据时，
 * 可以认为这个小组组内已经达到了有序，然后再合并相邻的二个小组就可以了。
 * 这样通过先递归的分解数列，再合并数列就完成了归并排序。
 *
 * 算法性能分析：
 * 时间复杂度 O(nlogn)
 * 空间复杂度 O(n + logn)
 * 排序时间与输入无关，最佳情况，最坏情况都是如此。
 *
 * 归并排序的时间复杂度，合并耗费O(n)时间，而由完全二叉树的深度可知，
 * 整个归并排序需要进行logn次，因此，总的时间复杂度为 O(nlogn)，
 * 而且这是归并排序算法中最好、最坏、平均的时间性能。
 *
 * 由于归并排序在归并过程中需要与原始记录序列同样数量的存储空间存放归并结果，
 * 以及递归时深度为 logn 的栈空间，
 * 因此空间复杂度为 O(n + logn)。
 *
 * 另外，对代码进行仔细研究，发现 merge 函数中有 if(L[i]<R[j]) 语句，
 * 这就说明它需要两两比较，不存在跳跃，因此归并排序是一种稳定的排序算法。
 *
 * 也就是说，归并排序是一种比较占用内存，但却效率高且稳定的算法。
 *
 * 速度仅次于快速排序，一般用于对总体无序，但是各子项相对有序的数列。
 */
public class MergeSort {
    private static final Logger LOG = LoggerFactory.getLogger(MergeSort.class);

    private static void merge(int[] arr, int idxBeg, int idxMid, int idxEnd) {
        int[] tmpArr = new int[arr.length]; //只用到了某一段
        int idxT = idxBeg;

        int idxL1 = idxBeg;
        int idxL2 = idxMid + 1;

        //逐个归并
        while (idxL1 <= idxMid && idxL2 <= idxEnd) {
            if (arr[idxL1] > arr[idxL2]) {
                tmpArr[idxT++] = arr[idxL1++];
            } else {
                tmpArr[idxT++] = arr[idxL2++];
            }
        }
        //将左边剩余的归并
        while (idxL1 <= idxMid) {
            tmpArr[idxT++] = arr[idxL1++];
        }
        //将右边剩余的归并
        while (idxL2 <= idxEnd) {
            tmpArr[idxT++] = arr[idxL2++];
        }
        //从临时数组拷贝到原数组(一段)
        while (idxBeg <= idxEnd) {
            arr[idxBeg] = tmpArr[idxBeg++];
        }
    }

    public static void descendingSort(int[] arr, int idxBeg, int idxEnd) {
        if (idxBeg >= idxEnd) {
            return;
        }
        //(1)分割：二路归并排序里面有两个Sort，多路归并排序里面写多个Sort就可以了
        int idxMid = (idxBeg + idxEnd) / 2;
        descendingSort(arr, idxBeg, idxMid);
        descendingSort(arr, idxMid + 1, idxEnd);
        //(2)归并
        merge(arr, idxBeg, idxMid, idxEnd); //归并上述两段有序数组。
    }

    @Deprecated
    public static void main(String[] args) {
        int[] unsortedArr = new int[]{8, 16, 29, 37, 44, 44, 49, 52, 57, 59, 68};
        descendingSort(unsortedArr, 0, unsortedArr.length - 1);
        LOG.info("descendingArr={}", unsortedArr);
    }

}
