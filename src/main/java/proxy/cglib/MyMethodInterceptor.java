package proxy.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * {@link MyMethodInterceptor}
 *
 * http://www.cnblogs.com/yangming1996/p/9270179.html
 */
public class MyMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("Before: " + method);
        Object obj = methodProxy.invokeSuper(o, objects);
        System.out.println("After: " + method);
        return obj;
    }

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/tmp/");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Student.class);
        enhancer.setCallback(new MyMethodInterceptor());
        Student stu = (Student) enhancer.create();
        stu.sayHello();
        stu.run();
        stu.speak();
        stu.study();
    }

}
