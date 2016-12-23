package algorithm.sort.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

/**
 * 交换排序(2)-->快速排序(非递归)
 */
public class ExchangeSort_Quick_Nonrecursion {
    private static final Logger LOG = LoggerFactory.getLogger(ExchangeSort_Quick_Nonrecursion.class);

    private static int partition(int[] arr, int idxBeg, int idxEnd) {
        int idxL = idxBeg;
        int idxR = idxEnd;
        int valPivot = arr[idxL];

        if (idxBeg < idxEnd) {
            while (idxL < idxR) {
                while (idxL < idxR && arr[idxR] <= valPivot) {
                    idxR--;
                }
                if (idxL < idxR) {
                    arr[idxL] = arr[idxR];
                    idxL++;
                }
                while (idxL < idxR && arr[idxL] > valPivot) {
                    idxL++;
                }
                if (idxL < idxR) {
                    arr[idxR] = arr[idxL];
                    idxR--;
                }
            }
            arr[idxL] = valPivot;
        }
        return idxL;
    }

    public static void sort(int[] arr) {
        if (null != arr && arr.length > 1) {
            //存放开始与结束索引
            Stack<Integer> stack = new Stack<Integer>();
            stack.push(0);
            stack.push(arr.length - 1);
            //利用循环实现
            while (!stack.empty()) {
                int idxRight = stack.pop();
                int idxLeft = stack.pop();
                //如果最大索引小于等于左边索引，说明结束了
                if (idxRight > idxLeft) {
                    int idxPivot = partition(arr, idxLeft, idxRight);
                    if (idxLeft < idxPivot - 1) {
                        stack.push(idxLeft);
                        stack.push(idxPivot - 1);
                    }
                    if (idxPivot + 1 < idxRight) {
                        stack.push(idxPivot + 1);
                        stack.push(idxRight);
                    }
                }
            }
        }
    }

    @Deprecated
    public static void main(String[] args) {
        int[] arr = {8, 16, 29, 37, 44, 44, 49, 52, 57, 59, 68};
        sort(arr);
        LOG.info("{}", arr);
    }

}
