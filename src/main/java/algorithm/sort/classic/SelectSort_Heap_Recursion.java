package algorithm.sort.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 选择排序(2)-->堆排序
 * <pre/>
 *
 * https://segmentfault.com/a/1190000002595152
 *
 * 堆排序可以看作选择排序的进化版。
 * 选择排序通过遍历来选出最大(最小)元素，
 * 堆排序利用数据结构二叉堆更快的选出最大(最小)元素。
 * 堆排序有两个优势:
 * 1)最好、最坏、平均时间复杂度均为O(nlogn);
 * 2)可以原地进行，空间复杂度O(1)。
 *
 * 基本定义与思想：
 * “二叉堆”是“完全二叉树”或者“类似于完全二叉树”，分为最大堆和最小堆。
 * 特点：
 * 1.父节点的值总是大于等于(最大堆)或者小于等于(最小堆)任意子节点的值
 * 2.每个节点的左右子树又是一个“二叉堆”
 *
 * 完全二叉树：树里面除了最后一层其他层都是填满的；
 * 也正是因为这样，
 * 树里面每个节点的父、子节点序号都可以根据当前节点序号直接求出。
 *
 * 第一个节点编号 i=1 时：
 * Parent(i) = i/2
 * Left(i)   = 2*i
 * Right(i)  = 2*i+1
 *
 * 第一个节点编号 i=0 时：
 * Parent(i) = (i-1)/2
 * Left(i)   = 2*i + 1
 * Right(i)  = 2*i + 2
 *
 * ...1....2....3....4....5....6....7....8....9....10
 * ---------------------------------------------------
 * | 16 | 14 | 10 | 8  | 7  | 9  | 3  | 2  | 4  | 1  |
 * ---------------------------------------------------
 *
 * .......................(1)16
 * ........................./..\
 * ......................./.....\
 * ...................../........\
 * .................../...........\
 * ..............(2)14.........(3)10
 * ................/..\........../..\
 * ............../.....\......../....\
 * ............/........\....../......\
 * .......(4)8.......(5)7..(6)9.....(7)3
 * ......../..\......../
 * ....../.....\....../
 * ..(8)2...(9)4..(10)1
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
public class SelectSort_Heap_Recursion {
    private static final Logger LOG = LoggerFactory.getLogger(SelectSort_Heap_Recursion.class);

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * 最小堆堆化
     *
     * @param arr        数组形式的堆
     * @param idxNonleaf 非叶子节点位置
     * @param heapSize   堆大小
     */
    private static void minHeapify(int arr[], int idxNonleaf, int heapSize) {
        int idxL = 2 * idxNonleaf + 1; //left child node
        int idxR = 2 * idxNonleaf + 2; //right child node
        int idxSmallest = idxNonleaf;  //最终指向三者中的最小值
        if (idxL < heapSize) {
            if (arr[idxSmallest] > arr[idxL]) {
                idxSmallest = idxL; //如果大于左子节点，则idxSmallest指向idxL
            }
        }
        if (idxR < heapSize) {
            if (arr[idxSmallest] > arr[idxR]) {
                idxSmallest = idxR; //如果大于右子节点，则idxSmallest指向idxL
            }
        }
        if (idxSmallest != idxNonleaf) {
            swap(arr, idxNonleaf, idxSmallest);
            minHeapify(arr, idxSmallest, heapSize); //递归调整父节点变了的堆
        }
    }

    public static void descendingSort(int arr[]) {
        //1 求堆大小
        int heapSize = arr.length;

        //2 首次建堆
        int idxLastNonleaf = heapSize / 2 - 1;
        for (int i = idxLastNonleaf; i >= 0; i--) {
            minHeapify(arr, i, heapSize);
        }

        //3 选择排序
        for (int i = 0; i < arr.length - 1; i++) {
            //3.1 将堆化选择出的最小值放在堆最后
            swap(arr, 0, heapSize - 1);
            //3.2 缩小堆范围
            heapSize--;
            //3.3 堆化缩小的堆
            minHeapify(arr, 0, heapSize);
        }
    }

    @Deprecated
    public static void main(String[] args) {
        int[] arr = new int[]{8, 16, 29, 37, 44, 44, 49, 52, 57, 59, 68};
        LOG.info("before sort: {}", arr);
        descendingSort(arr);
        LOG.info("after  sort: {}", arr);
    }

}
