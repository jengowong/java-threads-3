package algorithm.sort.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 交换排序(1)-->冒泡排序https://segmentfault.com/a/1190000002595152
 * <pre/>
 *
 * 基本思想：
 * 它重复地走访过要排序的数列，一次比较两个元素，如果他们的顺序错误就把他们交换过来。
 * 走访数列的工作是重复地进行直到没有再需要交换，也就是说该数列已经排序完成。
 *
 * ........j i
 * -------------------------
 * | | | | | | | | | | | | |
 * -------------------------
 *
 * 算法性能分析：
 * 时间复杂度O(n^2)，空间复杂度O(1)，稳定，因为存在两两比较，不存在跳跃。
 * 排序时间与输入无关，最好，最差，平均都是O(n^2)
 *
 * 冒泡排序缺陷：
 * 1.在排序过程中，执行完当前的第i趟排序后，可能数据已全部排序完备，
 * ..但是程序无法判断是否完成排序，会继续执行剩下的(n-1-i)趟排序。
 * ..解决方法：设置一个flag位，如果一趟无元素交换，则flag=0；以后再也不进入第二层循环。
 * 2.当排序的数据比较多时排序的时间会明显延长，因为会比较 n*(n-1)/2 次。
 */
public class ExchangeSort_Bubble {
    private static final Logger LOG = LoggerFactory.getLogger(ExchangeSort_Bubble.class);

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void descendingSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = arr.length - 1; j >= i + 1; j--) {
                if (arr[j] > arr[j - 1]) {
                    swap(arr, j, j - 1);
                }
            }
        }
    }

    @Deprecated
    public static void main(String[] args) {
        int[] unsortedArr = new int[]{8, 16, 29, 37, 44, 44, 49, 52, 57, 59, 68};
        descendingSort(unsortedArr);
        LOG.info("descendingArr={}", unsortedArr);
    }

}
