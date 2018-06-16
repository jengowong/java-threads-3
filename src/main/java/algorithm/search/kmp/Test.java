package algorithm.search.kmp;

/**
 * @author jengowang
 * @since 2017-06-06
 */
public class Test {

    public static void main(String[] args) {
        StringMatcher matcher = new ViolentStringMatcher();
        System.out.println(matcher.indexOf("helloworld", "ow"));
        matcher = new KMPStringMatcher();
        System.out.println(matcher.indexOf("helloworld", "ow"));
        matcher = new OptimizedKMPStringMatcher();
        System.out.println(matcher.indexOf("helloworld", "ow"));
    }

}
