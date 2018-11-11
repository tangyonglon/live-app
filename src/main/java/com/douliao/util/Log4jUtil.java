package com.douliao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4jUtil {
	
	private static Log4jUtil instance = null;  
    private static Logger logger = null;  
  
    static {  
    	logger = LoggerFactory.getLogger(Log4jUtil.class); // 创建JCL的Log实例  
    }  
  
    private Log4jUtil() {  
    }  
  
    public static Log4jUtil getInstance() {  
        synchronized (Log4jUtil.class) {  
            if (instance == null) {  
                instance = new Log4jUtil();  
            }  
        }  
        return instance;  
    }  
  
    public static void debug(String str) {  
        logger.debug(str);  
    }  
  
    public static void debug(String str, Exception e) {  
        logger.debug(str, e);  
    }  
      
    public static void info(String str) {  
        logger.info(str);  
    }  
      
    public static void info(String str, Exception e) {  
        logger.info(str, e);  
    }  
  
    public static void warn(String str) {  
        logger.warn(str);  
    }  
      
    public static void warn(String str, Exception e) {  
        logger.warn(str, e);  
    }  
  
    public static void error(String str) {  
        logger.error(str);  
    }  
  
    public static void error(String str, Exception e) {  
        logger.error(str, e);  
    }  
      
}
