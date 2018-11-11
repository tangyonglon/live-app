package com.douliao.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Live_Deductions {
	
	public static void main(String[] args) throws ParseException {
		int time=getChatTime("2018-07-30 10:44:43.0","2018-07-30 10:44:55.0");
		System.out.println("聊天时长"+time);
	}
	
	/**
	 * 计算聊天费用
	 * @param startTime
	 * @param endTime
	 * @param price
	 * @return
	 * @throws ParseException
	 */
	public static double Deductions(String startTime,String endTime,double price) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		double minute=Math.ceil((double)((sdf.parse(endTime).getTime()-sdf.parse(startTime).getTime()))/(1000*60));
		double gold=minute*price;
		return gold;
	}
	
	/**
	 * 计算聊天时长
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	public static int getChatTime(String startTime,String endTime) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int minute=0;
		try {
			minute = (int) Math.ceil((double)((sdf.parse(endTime).getTime()-sdf.parse(startTime).getTime()))/(1000*60));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return minute;
	}
	
}
