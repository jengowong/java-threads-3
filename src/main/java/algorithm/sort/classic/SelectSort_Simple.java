package algorithm.sort.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 选择排序(1)-->简单选择排序
 * <pre/>
 *
 * https://www.zybuluo.com/frankstar/note/447336
 *
 * 基本思想：
 * 每趟从待排序的记录中选出关键字最小的记录，
 * 顺序放在已排序的记录序列末尾，直到全部排序结束为止。
 *
 * i --->
 * j --->
 * i j
 * -------------------------
 * | | | | | | | | | | | | |
 * -------------------------
 *
 * 算法性能分析：
 * 简单选择排序的比较次数与序列的初始排序无关。
 * 假设待排序的序列有 n 个元素，则比较次数总是 n(n-1)/2。
 * 而移动次数与序列的初始排序有关。当序列正序时，移动次数最少，为0。
 * 当序列反序时，移动次数最多，为 3n(n-1)/2。
 * 所以，综合以上，简单排序的时间复杂度为 O(n^2)。
 */
public class SelectSort_Simple {
    private static final Logger LOG = LoggerFactory.getLogger(SelectSort_Simple.class);

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void descendingSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {     //按位置顺序插入值
            int idxMaxVal = i;
            for (int j = i + 1; j < arr.length; j++) { //最大数候选集
                if (arr[j] > arr[idxMaxVal]) {
                    idxMaxVal = j;
                }
            }
            if (idxMaxVal != i) {
                swap(arr, i, idxMaxVal);
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
