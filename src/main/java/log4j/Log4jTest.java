package log4j;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * https://my.oschina.net/xianggao/blog/518059
 */
public class Log4jTest {
    private static Logger logger = Logger.getLogger(Log4jTest.class);

    public static void main(String[] args) {
        // 从字面意思上看非常简单，我们使用了一个基础配置器，并调用其configure()方法，即配置方法完成了配置。
        BasicConfigurator.configure();
        logger.info("my first log4j info");
    }

}
