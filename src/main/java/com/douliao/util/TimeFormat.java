package com.douliao.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeFormat {
	
	public static String getSimple(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	public static String getDate() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
	
	/**
	 * 计算两个时间的差值是否大于一周
	 * @param time1
	 * @param time2
	 * @return
	 * @throws ParseException
	 */
	public static boolean sub(String time1,String time2) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long hours=sdf.parse(time1).getTime()-sdf.parse(time2).getTime();
		System.out.println(hours);
		if(hours>24*7) {
			return true;
		}else {
			return false;
		}
	}
	
	public static String getZero() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long current=System.currentTimeMillis();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
		return sdf.format(new Date(zero));
	}
	
	public static void main(String[] args) {
		System.out.println(getZero());
        long current=System.currentTimeMillis();//当前时间毫秒数
        long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
        long twelve=zero+24*60*60*1000-1;//今天23点59分59秒的毫秒数
        long yesterday=System.currentTimeMillis()-24*60*60*1000;//昨天的这一时间的毫秒数
        System.out.println(new Timestamp(current));//当前时间
        System.out.println(new Timestamp(yesterday));//昨天这一时间点
        System.out.println(new Timestamp(zero));//今天零点零分零秒
        System.out.println(new Timestamp(twelve));//今天23点59分59秒
	}
}
