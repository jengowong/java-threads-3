package log4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.Util;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Slf4j 日志门面接口 Test
 * https://my.oschina.net/xianggao/blog/519199
 */
public class Slf4jFacadeTest {
    private static Logger logger = LoggerFactory.getLogger(Slf4jFacadeTest.class);

    public static void main(String[] args) {
        if (logger.isDebugEnabled()) {
            logger.debug("slf4j-log4j debug message");
        }
        if (logger.isInfoEnabled()) {
            logger.debug("slf4j-log4j info message");
        }
        if (logger.isTraceEnabled()) {
            logger.debug("slf4j-log4j trace message");
        }
        logger.info("paths={}", findPossibleStaticLoggerBinderPathSet());

    }

    private static final String STATIC_LOGGER_BINDER_PATH = "org/slf4j/impl/StaticLoggerBinder.class";

    private static Set<URL> findPossibleStaticLoggerBinderPathSet() {
        // use Set instead of list in order to deal with bug #138
        // LinkedHashSet appropriate here because it preserves insertion order during iteration
        Set<URL> staticLoggerBinderPathSet = new LinkedHashSet<URL>();
        try {
            ClassLoader loggerFactoryClassLoader = LoggerFactory.class.getClassLoader();
            Enumeration<URL> paths;
            if (loggerFactoryClassLoader == null) {
                paths = ClassLoader.getSystemResources(STATIC_LOGGER_BINDER_PATH);
            } else {
                paths = loggerFactoryClassLoader.getResources(STATIC_LOGGER_BINDER_PATH);
            }
            while (paths.hasMoreElements()) {
                URL path = (URL) paths.nextElement();
                staticLoggerBinderPathSet.add(path);
            }
        } catch (IOException ioe) {
            Util.report("Error getting resources from path", ioe);
        }
        return staticLoggerBinderPathSet;
    }

}
