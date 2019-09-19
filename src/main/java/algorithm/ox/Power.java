package algorithm.ox;

/**
 * {@link Power}
 *
 * https://mp.weixin.qq.com/s/M0EPHU29cYv_cNzVialJQA
 */
public class Power {

    /**
     * 时间复杂度 O(logn)
     *
     * @param x 底数
     * @param n 指数
     *
     * @return 幂
     */
    public static double pow1(double x, int n) {
        if (0 == n) {
            return 1;
        }
        double t = pow1(x, n / 2);
        if (0 != (n % 2)) {
            return x * t * t;
        }
        return t * t;
    }

    /**
     * 时间复杂度 O(n)
     *
     * @param x 底数
     * @param n 指数
     *
     * @return 幂
     */
    public static double pow2(double x, int n) {
        double result = 1;
        for (int i = 0; i < n; i++) {
            result = result * x;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(pow1(3, 5));
        System.out.println(pow2(3, 5));
    }

}
