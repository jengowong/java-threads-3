package algorithm.hash;

import lombok.extern.slf4j.Slf4j;

/**
 * 常见Hash算法的原理
 */
@Slf4j
public class CommonHash {

    /**
     * 01.加法Hash
     * <pre/>
     * 所谓的加法Hash就是把输入元素一个一个的加起来构成最后的结果。
     *
     * @param key   关键码
     * @param prime 随意的质数
     *
     * @return 结果的值域为[0, prime-1]
     */
    public static int additiveHash(String key, int prime) {
        int hash = key.length();
        for (int i = 0; i < key.length(); i++) {
            hash += key.charAt(i);
        }
        return (hash % prime);
    }

    /**
     * 02.位运算Hash
     * <pre/>
     * 这类型Hash函数通过利用各种位运算（常见的是移位和异或）来充分的混合输入元素。
     * 先移位，然后再进行各种位运算是这样的类型Hash函数的主要特点。
     *
     * @param key   关键码
     * @param prime 随意的质数
     *
     * @return 结果的值域为[0, prime-1]
     */
    public static int rotatingHash(String key, int prime) {
        int hash = key.length();
        for (int i = 0; i < key.length(); i++) {
            //1
            hash = (hash << 4) ^ (hash >> 28) ^ key.charAt(i);
            //2
            //hash = (hash << 5) ^ (hash >> 27) ^ key.charAt(i);
            //3
            //hash += key.charAt(i);
            //hash += (hash << 10);
            //hash ^= (hash >> 6);
            //4
            //if ((i & 1) == 0) {
            //    hash ^= (hash << 7) ^ key.charAt(i) ^ (hash >> 3);
            //} else {
            //    hash ^= ~((hash << 11) ^ key.charAt(i) ^ (hash >> 5));
            //}
            //5
            //hash += (hash << 5) + key.charAt(i);
            //6
            //hash = key.charAt(i) + (hash << 6) + (hash >> 16) - hash;
            //7
            //hash ^= ((hash << 5) + key.charAt(i) + (hash >> 2));
        }
        return (hash % prime);
    }

    /**
     * 03.乘法Hash
     * <pre/>
     *
     * 这种类型的Hash函数利用了乘法的不相关性（乘法的这种性质，最有名的莫过于平方取头尾的随机数生成算法，虽然这种算法效果并不好）。
     *
     * jdk5.0里面的String类的hashCode()方法也使用乘法Hash。
     * 不过，它使用的乘数是31。
     * 推荐的乘数还有：131, 1313, 13131, 131313等等。
     *
     * @param key 关键码
     *
     * @return
     */
    public static int bernstein(String key) {
        int hash = 0, i;
        for (i = 0; i < key.length(); ++i) {
            hash = 33 * hash + key.charAt(i);
        }
        return hash;
    }


    /**
     * 测试位运算符
     */
    public static void testBitwiseOperators() {
        // 1、左移( << )
        // 0000 0000 0000 0000 0000 0000 0000 0101 然后左移2位后，低位补0：//
        // 0000 0000 0000 0000 0000 0000 0001 0100 换算成10进制为20
        log.info("(5 << 2) = {}", 5 << 2); // 运行结果是20

        // 2、右移( >> ) 高位补符号位
        // 0000 0000 0000 0000 0000 0000 0000 0101 然后右移2位，高位补0：
        // 0000 0000 0000 0000 0000 0000 0000 0001
        log.info("(5 >> 2) = {}", 5 >> 2); // 运行结果是1

        // 3、无符号右移( >>> ) 高位补0
        // 例如 -5换算成二进制后为：0101 取反加1为1011
        // 1111 1111 1111 1111 1111 1111 1111 1011
        // 我们分别对5进行右移3位、 -5进行右移3位和无符号右移3位：
        log.info("(5 >> 3) = {}", 5 >> 3);     // 结果是0
        log.info("(-5 >> 3) = {}", -5 >> 3);   // 结果是-1
        log.info("(-5 >>> 3) = {}", -5 >>> 3); // 结果是536870911

        // 4、位与( & )
        // 位与：第一个操作数的的第n位于第二个操作数的第n位如果都是1，那么结果的第n为也为1，否则为0
        log.info("(5 & 3) = {}", 5 & 3); // 结果为1
        log.info("(4 & 1) = {}", 4 & 1); // 结果为0

        // 5、位或( | )
        // 第一个操作数的的第n位于第二个操作数的第n位 只要有一个是1，那么结果的第n为也为1，否则为0
        log.info("(5 | 3) = {}", 5 | 3); // 结果为7

        // 6、位异或( ^ )
        // 第一个操作数的的第n位于第二个操作数的第n位 相反，那么结果的第n为也为1，否则为0
        log.info("(5 ^ 3) = {}", 5 ^ 3); //结果为6

        // 7、位非( ~ )
        // 操作数的第n位为1，那么结果的第n位为0，反之。
        log.info("(~5) = {}", ~5);// 结果为-6

        int a;
        a = 0xfffffffb;
        log.info("(0xfffffffb) = {}", a);
        a = 0x1fffffff;
        log.info("(0x1fffffff) = {}", a);
        a = 0xffffffff;
        log.info("(0xffffffff) = {}", a);
    }

    @Deprecated
    public static void main(String[] args) {
        testBitwiseOperators();
    }

}
