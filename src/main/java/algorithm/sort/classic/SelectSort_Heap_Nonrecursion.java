package algorithm.sort.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * https://www.zybuluo.com/frankstar/note/447336#堆排序
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
 */
public class SelectSort_Heap_Nonrecursion {
    private static final Logger LOG = LoggerFactory.getLogger(SelectSort_Heap_Recursion.class);

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * 对数组从0到最大索引值建堆
     *
     * @param arr    数组形式的堆
     * @param idxMax 数组最大下标
     */
    private static void maxHeapify(int[] arr, int idxMax) {
        int idxLastNonleaf = (idxMax - 1) / 2;               //从最后一个节点的父节点开始
        for (int idxNonleaf = idxLastNonleaf; idxNonleaf >= 0; idxNonleaf--) {      //**负责非叶节点的倒序处理
            int idxParent = idxNonleaf;                      //当前节点
            while ((2 * idxParent + 1) <= idxMax) {          //如果当前节点的子节点存在  //**负责非叶节点的正序处理
                int idxChild = 2 * idxParent + 1;            //"左"子节点
                if (idxChild < idxMax) {                     //注意此处没有等号
                    if (arr[idxChild] < arr[idxChild + 1]) { //如果"右"子节点比较大
                        idxChild++;                          //记录右节点的索引值
                    }
                }
                if (arr[idxParent] < arr[idxChild]) {
                    swap(arr, idxParent, idxChild);
                    idxParent = idxChild;
                } else {
                    break;
                }
            }
        }
    }

    public static void ascendingSort(int arr[]) {
        for (int idxMax = arr.length - 1; idxMax >= 0; idxMax--) {
            maxHeapify(arr, idxMax);
            swap(arr, 0, idxMax);
        }
    }

    @Deprecated
    public static void main(String[] args) {
        //int[] arr = {8, 16, 29, 37, 44, 44, 49, 52, 57, 59, 68};
        int[] arr = {1, 3, 4, 5, 7, 2, 6, 8, 0};
        LOG.info("before sort: {}", arr);
        ascendingSort(arr);
        LOG.info("after  sort: {}", arr);
    }

}
