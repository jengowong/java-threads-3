package algorithm.sort.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 基数排序
 * <pre/>
 *
 * 基本思想：
 * 按照各个分位分别进行排序，将所有待比较数值(正整数)统一为同样的数位长度，数位较短的数前面补零。
 * 然后，从最低位开始，依次进行一次排序。
 * 这样从最低位排序一直到最高位排序完成以后，数列就变成一个有序序列。
 *
 * ........j i
 * -------------------------
 * | | | | | | | | | | | | |
 * -------------------------
 *
 * 算法性能分析：
 * 这个算法还是比较有想法的。
 * 基数排序的时间复杂度为O(nlog(r)m)，r为位数，m为基数。
 */
public class RadixSort {
    private static final Logger LOG = LoggerFactory.getLogger(RadixSort.class);

    public static int[] descendingSort(int[] arr) {
        //找到最大数 确定要排序几趟
        int max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
        }

        //判断有几位
        int times = 0;
        while (max > 0) {
            max /= 10;
            times++;
        }

        //建立十个桶
        List<ArrayList> buckets = new ArrayList<ArrayList>();
        for (int i = 0; i < 10; i++) {
            buckets.add(new ArrayList());
        }

        //将相应的数放在相应的桶中
        for (int i = 0; i < times; i++) {
            for (int j = 0; j < arr.length; j++) {
                int x = arr[j] % (int) Math.pow(10, i + 1) / (int) Math.pow(10, i);
                ArrayList temp = buckets.get(x);
                temp.add(arr[j]);
                buckets.set(x, temp);
            }

            //收集
            int count = 0;
            for (int k = 0; k < 10; k++) {
                while (buckets.get(k).size() > 0) {
                    ArrayList<Integer> result = buckets.get(k);
                    arr[count] = result.get(0);
                    result.remove(0);
                    count++;
                }
            }
        }
        return arr;
    }

    @Deprecated
    public static void main(String[] args) {
        int[] unsortedArr = new int[]{57, 68, 59, 52, 44, 49, 29, 37, 8, 16};
        int[] sortedArr = descendingSort(unsortedArr);
        LOG.info("descendingArr={}", sortedArr);
    }

}
