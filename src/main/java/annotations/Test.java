package annotations;

import java.lang.reflect.Method;

/**
 * {@link Test}
 */
public class Test {

    @Hello("hello")
    public static void main(String[] args) throws Exception {
        Class cls = Test.class;
        Method method = cls.getMethod("main", String[].class);
        Hello hello = method.getAnnotation(Hello.class);
        System.out.println(hello.value());
    }
}
