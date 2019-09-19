package algorithm.priorityqueue;

import java.util.Comparator;

/**
 * {@link} Heap 堆数据结构
 * <pre>
 * 1. 序列Ki, K(i+1), K(i+2), ..., Kn, 当且仅当该序列满足如下性质称为堆
 *    当i从1起算时, Ki <= K(2i)  且Ki <= K(2i+1) (最小堆) 或 Ki >= K(2i)  且ki >= K(2i+1) (最大堆);
 *    当i从0起算时, Ki <= K(2i+1)且Ki <= K(2i+2) (最小堆) 或 Ki >= K(2i+1)且Ki >= K(2i+2) (最大堆);
 *    代码里一般都是从0起算.
 *
 * 2. 堆一般用顺序存储结构存储(数组)，但逻辑上可以认为是一个完全二叉树。
 *
 * 3. 若设二叉树的深度为h, 除第h层外, 其它各层(1～h-1)的结点数都达到最大个数,
 *    第h层所有的结点都连续集中在最左边, 这就是完全二叉树。
 */
public class Heap<T> {

    /** 以数组形式存储堆 */
    private T[] a;
    /** 用于比较堆中的元素 */
    private Comparator<? super T> c;

    public Heap(T[] a, Comparator<? super T> c) {
        this.a = a;
        this.c = c;
        buildHeap();
    }

    /**
     * 计算给定节点下标i的父节点下标
     *
     * @param i 节点下标
     *
     * @return 父节点下标
     */
    private int parent(int i) {
        return (i - 1) >>> 1;
    }

    /**
     * 计算给定节点小标i的left子节点下标
     *
     * @param i 节点下标
     *
     * @return left子节点下标
     */
    private int left(int i) {
        return (i << 1) + 1;
    }

    /**
     * 计算给定节点下标i的right子节点下标
     *
     * @param i 节点下标
     *
     * @return right子节点下标
     */
    private int right(int i) {
        return (i << 1) + 2;
    }

    /**
     * 堆化
     *
     * @param i 堆化的起始节点下标
     */
    private void heapify(int i) {
        heapify(i, a.length);
    }

    /**
     * 堆化
     *
     * @param i    堆化的起始节点下标
     * @param size 堆化的范围
     */
    private void heapify(int i, int size) {
        int l = left(i);
        int r = right(i);
        int next = i;
        if (l < size && c.compare(a[next], a[l]) > 0) { //root比left
            next = l;
        }
        if (r < size && c.compare(a[next], a[r]) > 0) { //left比right
            next = r;
        }
        if (i == next) {
            return;
        }
        swap(i, next);
        heapify(next, size);
    }

    /**
     * 交换
     *
     * @param i 下标i
     * @param j 下标j
     */
    private void swap(int i, int j) {
        T tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    /**
     * 对堆进行排序
     */
    public void sort() {
        // buildHeap();
        for (int i = a.length - 1; i > 0; i--) {
            swap(0, i);
            heapify(0, i);
        }
    }

    /**
     * 构建二叉堆，也就是把一个无序的完全二叉树调整为二叉堆，本质上就是让所有"非叶子节点"依次下沉。
     */
    private void buildHeap() {
        for (int i = (a.length >>> 1) - 1; i >= 0; i--) { //-1是因为idx从0开始
            heapify(i);
        }
    }

    public void setRoot(T root) {
        a[0] = root;
        heapify(0);
    }

    public T root() {
        return a[0];
    }


    @Deprecated
    public static void main(String[] args) {
        Integer[] a = new Integer[]{36, 40, 50, 35, 39, 22, 21, 30, 20, 14};
        Comparator<Integer> c =
                new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        //生成最小堆使用o1-o2,生成最大堆使用o2-o1
                        return o1 - o2;
                    }
                };
        Heap<Integer> heap = new Heap<>(a, c);
        for (int i : a) {
            System.out.print(i + " ");
        }
        System.out.println();

        heap.sort();
        for (int i : a) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println(5 >>> 1);

        System.out.println(heap.parent(0));
    }

}
