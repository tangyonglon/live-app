package com.douliao.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CreateOrder {
	
	public static void main(String[] args) {
//		System.out.println(getOrderIdByTime20());
		System.out.println(createLiveRoomId());
	}
	
	
	public static String createLiveRoomId() {
		String result="";
		Random random=new Random();
        for(int i=0;i<8;i++){
            result+=random.nextInt(10);
        }
        return result;
	}
	
	
	/**
	 * 生成20位字符串
	 * @return
	 */
	public static String getOrderIdByTime20() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }
	
	/**
	 * 生成21位字符串
	 * @return
	 */
	public static String getOrderIdByTime21() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<4;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }
	
	/**
	 * 生成22位字符串
	 * @return
	 */
	public static String getOrderIdByTime22() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<5;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }
}
