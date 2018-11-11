package com.douliao.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 读取配置文件工具
 *
 * @author Huangst2016
 * @version v1.0
 * @create 2017年11月14日 13:28
 **/
public class ReadResourceConfigUtils {
	
	public static void main(String[] args) {
        new ReadResourceConfigUtils().getRedisConfig("/config/agora.properties","appID");
    }
	
	public String getRedisConfig(String path,String key){
        Properties prop = new Properties();
        try {
            prop.load(this.getClass().getResourceAsStream(path));
            String appID=prop.getProperty(key);
            System.out.println(appID);
             return appID;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
