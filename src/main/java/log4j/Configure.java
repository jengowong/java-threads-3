package log4j;

import org.apache.log4j.Logger;

import java.util.Properties;

public class Configure {
    // 同样，首先得到和这个类绑定的Logger实例
    private static Logger log = Logger.getLogger(Configure.class);

    public void config() {
        log.info("using default db.properties");
        config("db.properties");
    }

    public void config(String resourceName) {
        log.info("using config file in classpath:" + resourceName);
        try {
            Properties prop = new Properties();
            prop.load(this.getClass().getClassLoader().getResourceAsStream(resourceName));
            log.debug("load properties file success");
            for (String key : prop.stringPropertyNames()) {
                String value = prop.getProperty(key);
                // doSomeConfigWorkUseKeyAndValue(key,value)
                log.debug("[properties] " + key + " : " + value);
            }
        } catch (Exception e) {
            log.error("log properties file failed", e);
        }
    }
}
