package proxy.dynamics;

import proxy.IService;
import proxy.RealClass;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * {@link MyHandler}
 */
public class MyHandler implements InvocationHandler {

    private Object realObj;

    public MyHandler(Object realObj) {
        this.realObj = realObj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy begining......");
        Object result = method.invoke(realObj, args);
        System.out.println("proxy ending......");
        return result;
    }

    public static void main(String[] args) {
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        RealClass realClass = new RealClass();
        MyHandler myHandler = new MyHandler(realClass);
        IService obj = (IService) Proxy.newProxyInstance(realClass.getClass().getClassLoader(), new Class[]{IService.class}, myHandler);
        obj.sayHello();
    }

}
