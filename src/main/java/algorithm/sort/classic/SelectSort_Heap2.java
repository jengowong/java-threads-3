package algorithm.sort.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * https://github.com/wangkuiwu/datastructs_and_algorithm/blob/master/source/algrightm/sort/heap_sort/java/HeapSort.java
 */
public class SelectSort_Heap2 {
    private static final Logger LOG = LoggerFactory.getLogger(SelectSort_Heap_Recursion.class);

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    //------------------------------------------------------------------

    /**
     * (最大)堆的向下调整算法
     * <pre/>
     * 注：
     * 数组实现的堆中，
     * 第N个节点的左孩子的索引值是(2N+1)，右孩子的索引是(2N+2)。
     * 其中，N为数组下标索引值，如数组中第1个数对应的N为0。
     *
     * @param arr    待排序的数组
     * @param idxBeg 被下调节点的起始位置(一般为0，表示从第1个开始)
     * @param idxEnd 截止范围(一般为数组中最后一个元素的索引)
     */
    private static void maxHeapDown(int[] arr, int idxBeg, int idxEnd) {
        int idxP = idxBeg;       //当前(current)节点的位置
        int idxL = 2 * idxP + 1; //左(left)孩子的位置
        int valP = arr[idxP];    //当前(current)节点的大小

        for (; idxL <= idxEnd; idxP = idxL, idxL = 2 * idxL + 1) {
            //"l"是左孩子，"l+1"是右孩子
            if (idxL < idxEnd && arr[idxL] < arr[idxL + 1]) {
                idxL++; //左右两孩子中选择较大者，即m_heap[l+1]
            }
            if (valP >= arr[idxL]) {
                break; //调整结束
            } else {   //交换值
                arr[idxP] = arr[idxL];
                arr[idxL] = valP;
            }
        }
    }

    /**
     * 堆排序(从小到大)
     *
     * @param arr  待排序的数组
     * @param size 数组的长度
     */
    public static void heapSortAsc(int[] arr, int size) {
        // 从(n/2-1) --> 0逐次遍历。遍历之后，得到的数组实际上是一个(最大)二叉堆。
        for (int i = size / 2 - 1; i >= 0; i--) {
            maxHeapDown(arr, i, size - 1);
        }

        // 从最后一个元素开始对序列进行调整，不断的缩小调整的范围直到第一个元素
        for (int i = size - 1; i > 0; i--) {
            // 交换a[0]和a[i]。交换后，a[i]是a[0...i]中最大的。
            swap(arr, 0, i);
            // 调整a[0...i-1]，使得a[0...i-1]仍然是一个最大堆。
            // 即，保证a[i-1]是a[0...i-1]中的最大值。
            maxHeapDown(arr, 0, i - 1);
        }
    }

    //------------------------------------------------------------------

    /**
     * (最小)堆的向下调整算法
     *
     * 注：数组实现的堆中，第N个节点的左孩子的索引值是(2N+1)，右孩子的索引是(2N+2)。
     * 其中，N为数组下标索引值，如数组中第1个数对应的N为0。
     *
     * @param arr    待排序的数组
     * @param begIdx 被下调节点的起始位置(一般为0，表示从第1个开始)
     * @param endIdx 截止范围(一般为数组中最后一个元素的索引)
     */
    private static void minHeapDown(int[] arr, int begIdx, int endIdx) {
        int idxP = begIdx;       //当前(current)节点的位置
        int idxL = 2 * idxP + 1; //左(left)孩子的位置
        int valP = arr[idxP];    //当前(current)节点的大小

        for (; idxL <= endIdx; idxP = idxL, idxL = 2 * idxL + 1) {
            //"l"是左孩子，"l+1"是右孩子
            if (idxL < endIdx && arr[idxL] > arr[idxL + 1]) {
                idxL++; //左右两孩子中选择较小者
            }
            if (valP <= arr[idxL]) {
                break; //调整结束
            } else {   //交换值
                arr[idxP] = arr[idxL];
                arr[idxL] = valP;
            }
        }
    }

    /**
     * 堆排序(从大到小)
     *
     * @param arr  待排序的数组
     * @param size 数组的长度
     */
    public static void heapSortDesc(int[] arr, int size) {
        //从(n/2-1) --> 0逐次遍历每。遍历之后，得到的数组实际上是一个最小堆。
        for (int i = size / 2 - 1; i >= 0; i--) {
            minHeapDown(arr, i, size - 1);
        }

        //从最后一个元素开始对序列进行调整，不断的缩小调整的范围直到第一个元素
        for (int i = size - 1; i > 0; i--) {
            //交换a[0]和a[i]。交换后，a[i]是a[0...i]中最小的。
            swap(arr, 0, i);
            //调整a[0...i-1]，使得a[0...i-1]仍然是一个最小堆。
            //即，保证a[i-1]是a[0...i-1]中的最小值。
            minHeapDown(arr, 0, i - 1);
        }
    }

    @Deprecated
    public static void main(String[] args) {
        System.out.println("降序排序:");
        int[] arr1 = {8, 16, 29, 37, 44, 44, 49, 52, 57, 59, 68};
        System.out.printf("before sort: ");
        for (int e : arr1) {
            System.out.printf("%d\t", e);
        }
        System.out.println();
        heapSortDesc(arr1, arr1.length);
        System.out.printf("after  sort: ");
        for (int e : arr1) {
            System.out.printf("%d\t", e);
        }

        System.out.println();

        System.out.println("升序排序:");
        int[] arr2 = {68, 59, 57, 52, 49, 44, 44, 37, 29, 16, 8};
        System.out.printf("before sort: ");
        for (int e : arr2) {
            System.out.printf("%d\t", e);
        }
        System.out.println();
        heapSortAsc(arr2, arr2.length);
        System.out.printf("after  sort: ");
        for (int e : arr2) {
            System.out.printf("%d\t", e);
        }
    }

}
