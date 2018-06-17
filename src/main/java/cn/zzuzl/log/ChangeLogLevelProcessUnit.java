package cn.zzuzl.log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.beans.factory.InitializingBean;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ChangeLogLevelProcessUnit implements InitializingBean {
    public static final ConcurrentMap<String, Object> loggerMap = new ConcurrentHashMap<String, Object>();

    // 初始化
    public void init() {
        String type = StaticLoggerBinder.getSingleton().getLoggerFactoryClassStr();
        System.out.println(type);
        if ("org.slf4j.impl.Log4jLoggerFactory".equals(type)) {
            Enumeration loggers = LogManager.getCurrentLoggers();
            while (loggers.hasMoreElements()) {
                org.apache.log4j.Logger logger = (org.apache.log4j.Logger) loggers.nextElement();
                System.out.println(logger.getName());
                if (logger.getLevel() != null) {
                    loggerMap.put(logger.getName(), logger);
                    logger.setLevel(Level.DEBUG);
                }
            }
            org.apache.log4j.Logger rootLogger = org.apache.log4j.LogManager.getRootLogger();
            loggerMap.put(rootLogger.getName(), rootLogger);
        }
    }

    public String getLoggerList() {
        JsonArray jsonArray = new JsonArray();
        for (ConcurrentMap.Entry<String, Object> entry : loggerMap.entrySet()) {
            JsonObject object = new JsonObject();
            object.addProperty("loggerName", entry.getKey());
            object.addProperty("logLevel", ((org.apache.log4j.Logger) entry.getValue()).getLevel().toString());
            jsonArray.add(object);
        }
        return jsonArray.toString();
    }

    public void setLogLevel() {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
}
