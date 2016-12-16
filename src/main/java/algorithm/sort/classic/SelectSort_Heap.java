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
 *          第一个节点编号为1时(广度遍历编号(下标))：
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
 * 时间复杂度 O(nlogn)，空间复杂度 O(1)。
 * 从这一点就可以看出，堆排序在时间上类似归并，
 * 但是它又是一种原地排序，空间复杂度小于归并的 O(n+logn)
 *
 * 排序时间与输入无关，最好、最差、平均都是 O(nlogn)。不稳定。
 *
 * 堆排序借助了堆这个数据结构，
 * 堆类似二叉树，又具有堆积的性质(子节点的关键值总小于/大于父节点)。
 *
 * 堆排序包括两个主要操作:
 * 1.保持堆的性质 heapify(A,i)，时间复杂度 O(logn)
 * 2.建堆 buildMaxHeap(A)，时间复杂度 O(n)，线性时间建堆
 *
 * 对于大数据的处理：
 * 如果对100亿条数据选择 topk 数据，选择快速排序好还是堆排序好？
 * 答案是只能用堆排序。
 * 堆排序只需要维护一个 k 大小的空间，即在内存开辟 k 大小的空间。
 * 而快速排序需要开辟能存储 100 亿条数据的空间，which is impossible。
 */
public class SelectSort_Heap {
    private static final Logger LOG = LoggerFactory.getLogger(SelectSort_Heap.class);

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private static void heapify(int arr[], int idx, int heapSize) {
        int idxSmallest = idx;
        int idxLC = 2 * idx + 1;
        int idxRC = 2 * idx + 2;
        if (idxLC < heapSize) {
            if (arr[idx] > arr[idxLC]) {
                idxSmallest = idxLC;
            } else {
                idxSmallest = idx;
            }
        }
        if (idxRC < heapSize) {
            if (arr[idxSmallest] > arr[idxRC]) {
                idxSmallest = idxRC;
            }
        }
        if (idxSmallest != idx) {
            swap(arr, idx, idxSmallest);
            heapify(arr, idxSmallest, heapSize);
        }
    }

    private static void buildHeap(int arr[]) {
        int idxNonleaf = arr.length / 2 - 1;

        for (int i = idxNonleaf; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }
    }

    public static void descendingSort(int arr[]) {
        buildHeap(arr);

        int heapSize = arr.length;
        for (int i = 0; i < arr.length - 1; i++) {
            //swap the first and the last
            swap(arr, 0, heapSize - 1);

            //heapify the array
            heapSize--;
            heapify(arr, 0, heapSize);
        }
    }

    @Deprecated
    public static void main(String[] args) {
        int[] arr = new int[]{8, 16, 29, 37, 44, 44, 49, 52, 57, 59, 68};
        descendingSort(arr);
        LOG.info("descendingArr={}", arr);
    }

}
