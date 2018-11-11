package com.douliao.util;

public class Transform {
	public static void main(String[] args) {
		String string="1,2,3";
		int[] array=stringTransfromIntArray(string);
		for(int i:array) {
			System.out.println(i);
		}
		
		System.out.println(stringIsInt("123"));
	}
	
	/**
	 * string字符串转换成int数组
	 * @param str
	 * @return
	 */
	public static int[] stringTransfromIntArray(String str) {
		String[] strArray=str.split(",");
		int[] intArray=new int[strArray.length];
		for(int i=0;i<strArray.length;i++) {
			intArray[i]=Integer.parseInt(strArray[i]);
		}
		return intArray;
	}
	
	
	public static boolean stringIsInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
