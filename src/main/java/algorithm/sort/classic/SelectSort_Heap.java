package algorithm.sort.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 选择排序(2)-->堆排序
 * <pre/>
 *
 * 基本定义与思想：
 * 二叉堆是完全二叉树或者类似于完全二叉树，分为最大堆和最小堆。
 * 特点：
 * 1.父节点的值总是大于等于(最大堆)或者小于等于(最小堆)任意自节点的值
 * 2.每个节点的左右子树又是一个二叉堆
 *
 * 主要过程就是构建一个最大堆的过程，堆构建的过程从最后一个叶子节点的父节点开始，逐层递减
 *
 * i --->
 * <--- j
 * ........j i
 * -------------------------
 * | | | | | | | | | | | | |
 * -------------------------
 *
 * 算法性能分析：
 * 由于最大堆或者最小堆是一颗完全二叉树，那么其算法复杂度应该为建树的复杂度，故为O(log(N))
 */
public class SelectSort_Heap {
    private static final Logger LOG = LoggerFactory.getLogger(SelectSort_Heap.class);

    private static void buildMaxHeap(int[] arr, int maxIndex) {
        //从最后一个节点的父节点开始
        for (int i = (maxIndex - 1) / 2; i >= 0; i--) {
            int k = i;//父节点
            //如果当前节点的子节点存在
            while (k * 2 + 1 <= maxIndex) {
                //左子节点
                int leftIndex = 2 * k + 1;
                if (leftIndex < maxIndex) {
                    //如果右子节点比较大
                    if (arr[leftIndex] < arr[leftIndex + 1]) {
                        leftIndex++;
                    }
                }
                if (arr[k] < arr[leftIndex]) {
                    swap(arr, k, leftIndex);
                    k = leftIndex;
                } else {
                    break;
                }
            }
        }
    }

    private static void swap(int[] arr, int k, int maxIndex) {

    }

    public static int[] descendingSort(int[] arr) {
        return null;
    }

    @Deprecated
    public static void main(String[] args) {
        int[] unsortedArr = new int[]{57, 68, 59, 52, 44, 49, 29, 37, 8, 16};
        int[] sortedArr = descendingSort(unsortedArr);
        LOG.info("descendingArr={}", sortedArr);
    }

}
