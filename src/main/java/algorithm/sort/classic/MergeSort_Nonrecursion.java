package algorithm.sort.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 归并排序(非递归)
 */
public class MergeSort_Nonrecursion {
    private static final Logger LOG = LoggerFactory.getLogger(MergeSort_Nonrecursion.class);

    @Deprecated
    public static void main(String[] args) {
        int[] unsortedArr = new int[]{8, 16, 29, 37, 44, 44, 49, 52, 57, 59, 68};
        //descendingSort(unsortedArr, 0, unsortedArr.length - 1);
        LOG.info("descendingArr={}", unsortedArr);
    }

}
