package crack;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 破解charles
 * <pre/>
 * http://www.jianshu.com/p/46d29e60dd1b
 */
public class Charles {
    private static final Logger LOG = LoggerFactory.getLogger(Charles.class);

    public static void main(String[] args) {
        try {
            ClassPool classPool = ClassPool.getDefault();
            classPool.insertClassPath("/Users/jengowang/Downloads/charles.jar"); //复制出来charles.jar的文件路径

            CtClass ctClass = classPool.get("com.xk72.charles.License");
            CtMethod ctMethod = ctClass.getDeclaredMethod("a", null);
            ctMethod.setBody("{return true;}");
            ctMethod = ctClass.getDeclaredMethod("b", null);
            ctMethod.setBody("{return \"Jengo Wang\";}");
            ctClass.writeFile();
        } catch (Exception e) {
            LOG.error("破解异常", e);
        }

    }

}
