package algorithm.sort.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 归并排序(非递归)
 */
public class MergeSort_Nonrecursion {
    private static final Logger LOG = LoggerFactory.getLogger(MergeSort_Nonrecursion.class);

    private static void merge(int[] arr, int idxBeg, int idxMid, int idxEnd) {
        int[] tmpArr = new int[arr.length]; //只用到了某一段
        int idxT = idxBeg;

        int idxL1 = idxBeg;     //前半段开始索引
        int idxL2 = idxMid + 1; //后半段开始索引

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

    /**
     * 2-路归并排序的非递归算法。
     * 算法思路：
     * 从归并段的长度为1开始，一次使归并段的长度变为原来的2倍。
     * 在每趟归并的过程中，要注意处理归并段的长度为奇数和
     * 最后一个归并段的长度和前面的不等的情况
     */
    public static void descendingSort(int[] arr) {
        //程序边界的处理非常重要
        for (int step = 1; step <= arr.length; step *= 2) {
            for (int i = 0; i + step <= arr.length - 1; i += step * 2) {
                int idxL = i, idxM = i + step - 1, idxR = i + step * 2 - 1;
                if (idxR > arr.length - 1) idxR = arr.length - 1;
                merge(arr, idxL, idxM, idxR);
            }
        }
    }

    @Deprecated
    public static void main(String[] args) {
        //int[] arr = new int[]{8, 16, 29, 37, 44, 44, 49, 52, 57, 59, 68};
        int[] arr = {1, 3, 4, 5, 7, 2, 6, 8, 0};
        //int[] arr = {3};
        LOG.info("before sort: {}", arr);
        descendingSort(arr);
        LOG.info("after  sort: {}", arr);
    }

}
