package proxy;

/**
 * {@link RealClass}
 */
public class RealClass implements IService {

    @Override
    public void sayHello() {
        System.out.println("hello world......");
    }

    public void doService() {
        System.out.println("doing service......");
    }

}
