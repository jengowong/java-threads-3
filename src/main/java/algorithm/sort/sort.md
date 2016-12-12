#排序算法

> 注：这里的排序不包括桶排序等特殊线性排序

***
##1.排序算法分为两个步骤: 
- 1.比较
- 2.移动

***
##2. 排序算法的目的: 
- 无序 --> 有序

***
##3.排序算法的分类
- 1.局部顺序 --> 全局顺序 --> 全序 (归并排序)
- 2.全局顺序 --> 全局顺序 (快排，堆排)

***
##4.算法的衡量标准
- 两个复杂度: 时间和空间(两种复杂度互斥)

***
##5.算法如何才能高效
我们从排序的两个基本步骤来讲，比较&移动。
比较是确认两个数的相对大小(that matter),
*高效的算法需要利用到每次比较的结果*。

***
##6.单线程情况下，为什么说最快的时间复杂度是 n*log(n)
我们以从*全局局序-->全局顺序*为例，
首先我们要确认某个值在整个数组中的全序我们就需要比较所有值，
为了高效我们需要利用每次比较。
eg：
快排比较后将小的放在左边，大的放在右边，
既得到了全局序，又利用了每次比较。(我们从贪心的角度来讲，这种算法已经是最优的了)

***
##选择排序
找到最小的元素与第一个元素交换；然后找第二小的与第二个交换，循环，直到完成。
大约需要 N^2/2 次比较和 N 次交换；时间复杂度 O(n^2)

***
##插入排序
从左向右找，查到一个不按顺序的，再往回逐个找，放到对应的位置。
从刚才的位置继续向右找，循环。
*所有索引左侧的都是有序的*。
一般情况下需要 n^2/4 次比较和 n^2/4 次交换。
在基本有序情况下，插入排序速度很快。
比较插入排序和选择排序，经过大量实验证明，*插入排序比选择排序快一倍左右*。

***
##希尔排序
每间隔 h 的元素构成子数组，对每个子数组用插入排序，这样每间隔 h 的子数组都是有序的。
然后逐渐减少h的值，当 h 值为 1 的时候，整个数据有序。
*因为有个间隔，这样每次移动，可以把大的值一次移动到更远的地方，提高了移动效率*。
排序之初，各个子数组都很短，排序后之后子数组都部分有序，这两种情况都很适合插入排序。
*h可以取值每次的 1/3*。
现在仍然没法准确算出希尔排序的性能特征，
但是经过实践证明，*希尔比插入快几百倍*。
运行时间不到平方级别，最差情况跟 N^(3/2) 成正比。

***
##归并排序
采用分制思想，将数组不断二分，最后排序后，再合并起来。
分两种：
- 自顶向下的归并：从整体顶层不断切分，递归，最后成2个数的比较，然后再一层层合并；
- 自底向上的归并：直接从1个数开始归并，然后2个数归并，然后4个数归并，知道全部归并完。比较适合链表数据结构的排序。
优化：对于递归到小数组，使用插入排序，比如小与15个，一般可以将时间缩短10%~15%。
归并排序对内存有额外的开销。
在整体效率上，对希尔排序的差距在常数级别。

***
##快速排序
每次找一个元素，通过移动，使得左侧的比这个小，右侧的比这个大。采用分治思想，递归解决。
优化：
- 对于小数组，使用插入排序；
- 三取样拆分，取三个数，去中位数作为排序基准；
- 熵最优的排序：采用三向切分，分成小于、等于、大于。适用于有大量重复的排序。

***
##堆排序
###优先队列
优先队列是种数据结构，支持两种操作：删除最大元素和插入元素。
二叉堆：
一个二叉树的每个结点都大于等于它的两个子节点。
如果用指针来表示，每个结点都需要三个指针。
但是如果树是完全二叉树，表示起来就非常方便，可以使用一个数组表示。
位置k的结点的父节点的位置为k/2。
而它的子节点的位置则分别为 2k 和 2k+1.
有序化的两种方式：
- (1)由下至上的堆有序化(上浮)
结点不断和父结点比较，如果比父节点大就交换。
- (2)由下至上的堆有序化(下沉)
结点不断和两个子节点比较，如果比子节点最大的小，则与之交换。
 
插入元素：将新元素放到堆尾，然后上浮到合适位置；
删除元素：将第一个元素删除，然后将最后一个元素放到第一位，然后下沉到合适位置。

###排序
不断取出最大的，然后修复堆，再取出最大的，循环。
对于给定数组的构建，只需要从k = N/2开始递减循环，修复堆，直到 k = 1 完成。
优化：在下沉过程中，只提升最大的子节点，直到到达堆底，然后再使元素上浮到正确位置。这个优化几乎将比较次数减少一半。
堆排序使用空间很小，时间也很快，适合在嵌入式等空间紧张的设备上使用。
 
中位数或者最大的k个数的求法：使用快速排序，不断的递归找分割点j，使得j右边的数字个数等于k。