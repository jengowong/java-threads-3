package algorithm.recursion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link Factorial}
 */
public class Factorial {
    private static final Logger LOG = LoggerFactory.getLogger(Factorial.class);

    public static int fact(int x) {
        if (x <= 1) {
            return 1;
        } else {
            return x * fact(x - 1);
        }
    }

    @Deprecated
    public static void main(String[] args) {
        LOG.info("fact={}", fact(5));
    }

}
