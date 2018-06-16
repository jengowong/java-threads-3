package log4j;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;

public class LogTest2 {

    @Test
    public void testLog2() {
        BasicConfigurator.configure();
        Configure conf = new Configure();
        conf.config();
    }

    @Test
    public void testLog3() {
        BasicConfigurator.configure();
        Configure conf = new Configure();
        conf.config("aa.properties");
    }

    /**
     * 说明Logger自身维护着每一个名字的Logger实例的引用，
     * 保证相同名字的Logger在不同地方获取到的实例是一致的，
     * 这样就允许我们在统一的代码中配置不同Logger的特性。
     */
    @Test
    public void testLog4() {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.INFO);
        Configure conf = new Configure();
        conf.config();
    }

    @Test
    public void testLoggerName() {
        BasicConfigurator.configure();
        Logger log1 = Logger.getLogger("a");
        Logger log2 = Logger.getLogger("a");
        log1.info(log1 == log2);
    }

    @Test
    public void testLogLevel2() {
        BasicConfigurator.configure();

        Logger logger = Logger.getLogger("cd.itcast");
        logger.getLoggerRepository().setThreshold(Level.WARN);
        Logger barLogger = Logger.getLogger("cd.itcast.log");

        logger.warn("logger warn");
        logger.debug("logger debug");
        barLogger.info("bar logger info");
        barLogger.debug("bar logger debug");
    }

}
