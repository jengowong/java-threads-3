package algorithm.sort.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * 选择排序(2)-->堆排序
 * <pre/>
 *
 * 基本定义与思想：
 * "二叉堆"是"完全二叉树"或者"类似于完全二叉树"，分为最大堆和最小堆。
 * 特点：
 * 1.父节点的值总是大于等于(最大堆)或者小于等于(最小堆)任意自节点的值
 * 2.每个节点的左右子树又是一个"二叉堆"
 *
 * 完全二叉树：树里面除了最后一层其他层都是填满的；
 *          也正是因为这样，
 *          树里面每个节点的子女和双亲节点的序号都可以根据当前节点的序号直接求出。
 *
 *          第一个节点编号为1时：
 *          Parent(i) = i/2
 *          Left(i)   = 2*i
 *          Right(i)  = 2*i+1
 *
 *                       (1)16
 *                         / \
 *                       /    \
 *                     /       \
 *                   /          \
 *              (2)14        (3)10
 *                /  \          / \
 *              /     \        /   \
 *            /        \      /     \
 *       (4)8       (5)7  (6)9   (7)3
 *        / \         /
 *      /    \       /
 * (8)2   (9)4  (10)1
 *
 *          第一个节点编号为0时：
 *
 * 主要过程就是构建一个最大堆的过程，堆构建的过程从最后一个叶子节点的父节点开始，逐层递减
 *
 *             1                 1                 1                 1                .1                 8                 8
 *           /  \              /  \              /  \              /  \              /  \              /  \              /  \
 *         3     4           3    .4          .3     6           8     6           8     6          .1     6           7     6
 *       / \   /  \        / \   /  \        / \   /  \        / \   /  \        / \   /  \        / \   /  \        / \   /  \
 *    .5   7  2   6      8   7  2   6      8   7  2   4     .3   7  2   4      5   7  2   4      5   7  2   4     .5   1  2   4
 *   / \               / \               / \               / \               / \               / \               / \
 * 8   0             5   0             5   0             5   0             3   0             3   0             3   0
 *
 * 算法性能分析：
 * 时间复杂度在worst-case是O(NlogN)，average-case是O(NlogN)；
 * 空间复杂度在worst-case是O(1)，也就是说heapsort可以in-place实现；
 * heapsort不稳定。
 *
 * 由于最大堆或者最小堆是一颗完全二叉树，那么其算法复杂度应该为建树的复杂度，故为O(N*logN)
 *
 * 堆排序和合并排序一样，是一种时间复杂度为O(N*logN)的算法，
 * 同时和插入排序一样，是一种就地排序算法(不需要额外的存储空间)。
 */
public class SelectSort_Heap {
    private static final Logger LOG = LoggerFactory.getLogger(SelectSort_Heap.class);

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void buildMaxHeap(int[] arr) {
        int idxMax = arr.length - 1;

        //从最后一个节点的父节点开始
        for (int i = (idxMax - 1) / 2; i >= 0; i--) {
            int k = i;//父节点
            //如果当前节点的子节点存在
            while (k * 2 + 1 <= idxMax) {
                //左子节点
                int leftIndex = 2 * k + 1;
                if (leftIndex < idxMax) {
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

    @Deprecated
    public static void main(String[] args) {
        int[] arr = new int[]{57, 68, 59, 52, 44, 49, 29, 37, 8, 16};
        buildMaxHeap(arr);
        LOG.info("descendingArr={}", arr);
    }

}
