package algorithm.sort.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 交换排序(2)-->快速排序
 * <pre/>
 *
 * 快速排序是对冒泡排序的一种改进，由C.A.R.Hoare在1962年提出。
 *
 * 基本思想：
 * 通过一趟排序将要排序的数据分割成独立的两部分，
 * 其中一部分的所有数据都比另外一部分的所有数据都要小，
 * 然后再按此方法对这两部分数据分别进行快速排序，
 * 整个排序过程可以递归进行，以此达到整个数据变成有序序列。
 *
 * 选择一个“基准元素”，通常选择“第一个”元素或者“最后一个”元素，
 * 通过一趟扫描，将待排序列分成两部分，一部分比“基准元素”小，一部分大于等于“基准元素”，
 * 此时“基准元素”在其排好序后的正确位置，然后再用同样的方法递归地排序划分的两部分。
 *
 * 实现思路：
 * ① 以第一个关键字 K1 为“控制字”，将 [K1,K2,...,Kn] 分成两个子区，
 * ...使左区所有关键字 <=K1，右区所有关键字 >=K1，
 * ...最后“控制字”居两个子区中间的适当位置，在子区内数据尚处于无序状态。
 * ② 把左区作为一个整体，用①的步骤进行处理，右区进行相同的处理。(即递归)
 * ③ 重复第①、②步，直到左区处理完毕。
 *
 * l p                     r
 * -------------------------
 * | | | | | | | | | | | | |
 * -------------------------
 *
 * 算法性能分析：
 * 时间复杂度O(nlogn)  空间复杂度O(logn)  不稳定
 *
 * 时间复杂度：
 * 最坏O(n^2)：当划分不均匀时候，逆序、排好序都是最坏情况
 * 最好O(n)：  当划分均匀
 * partition的时间复杂度O(logn)：一共需要logn次partition
 *
 * 空间复杂度：
 * 递归造成的栈空间的使用，
 * 最好情况O(logn)：递归树的深度logn
 * 最坏情况O(n)：   需要进行 n‐1 递归调用
 * 平均情况O(logn)：空间复杂度也为O(logn)
 *
 * 由于关键字的比较和交换是“跳跃”进行的，因此，快速排序是一种不稳定的排序方法。
 *
 * 快速排序的每一轮就是将这一轮的“基准数”归位，直到所有的数都归位为止，排序结束。(类似冒泡)。
 * partition是返回一个基准值的index，index左边都小于该index的数，右边都大于该index的数。
 *
 * 快速排序之所以比较快，因为相比冒泡排序，每次交换是“跳跃式”的。
 * 每次排序的时候设置一个“基准点”，
 * 将小于等于基准点的数全部放到基准点的左边，将大于等于基准点的数全部放到基准点的右边。
 * 这样在每次交换的时候就不会像冒泡排序一样每次只能在相邻的数之间进行交换，交换的距离就大的多了。
 * 因此总的比较和交换次数就少了，速度自然就提高了。
 * 当然在最坏的情况下，仍可能是相邻的两个数进行了交换。
 * 因此快速排序的最差时间复杂度和冒泡排序是一样的都是 O(n^2)，它的平均时间复杂度为 O(nlogn)。
 * 其实快速排序是基于“二分”的思想。
 */
public class ExchangeSort_Quick {
    private static final Logger LOG = LoggerFactory.getLogger(ExchangeSort_Quick.class);

    public static void descendingSort(int[] arr, int idxBeg, int idxEnd) {
        if (idxBeg < idxEnd) {
            int idxL = idxBeg;
            int idxR = idxEnd;

            int valBase = arr[idxBeg]; //选第一个数作为基准

            while (idxL < idxR) {
                while (idxL < idxR && valBase >= arr[idxR]) {
                    idxR--;
                }
                arr[idxL] = arr[idxR]; //right side: find larger one than valBase

                while (idxL < idxR && valBase <= arr[idxL]) {
                    idxL++;
                }
                arr[idxR] = arr[idxL]; //left side: find smaller one than valBase
            }
            arr[idxL] = valBase;

            descendingSort(arr, idxBeg, idxL - 1);
            descendingSort(arr, idxR + 1, idxEnd);
        }
    }

    @Deprecated
    public static void main(String[] args) {
        int[] arr = new int[]{8, 16, 29, 37, 44, 44, 49, 52, 57, 59, 68};
        descendingSort(arr, 0, arr.length - 1);
        LOG.info("descendingArr={}", arr);
    }

}
