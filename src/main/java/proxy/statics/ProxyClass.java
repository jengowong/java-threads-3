package proxy.statics;

import proxy.IService;
import proxy.RealClass;

/**
 * {@link ProxyClass}
 */
public class ProxyClass implements IService {

    private RealClass realClass;

    public ProxyClass(RealClass realClass) {
        this.realClass = realClass;
    }

    @Override
    public void sayHello() {
        System.out.println("i am proxy class, prepare for saying hello......");
        realClass.sayHello();
        System.out.println("i am proxy class, saying hello ending......");
    }

    public void doService() {
        System.out.println("i am proxy class, prepare for doing service......");
        realClass.doService();
        System.out.println("i am proxy class, doing service ending......");
    }

    public static void main(String[] args) {
        RealClass realClass = new RealClass();
        ProxyClass proxyClass = new ProxyClass(realClass);
        proxyClass.sayHello();
        proxyClass.doService();
    }

}
