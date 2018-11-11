package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class LoginParam {
	/**
	 * 手机号码归属地
	 */
	private String phoneCode;
	/**
	 * 手机号
	 */
	private String userPhone;
	/**
	 * 第三方的账号
	 */
	private String userAccount;
	/**
	 * 第三方账号类型  0 手机号 1.facebook
	 */
	private int account_type;
	/**
	 * 密码
	 */
	private String userPassword;
	/**
	 * 手机验证码
	 */
	private String code;
	/**
	 * 设备唯一标识
	 */
	private String mac;
	/**
	 * 设备类型 1.手机 2.PC
	 */
	private String macType;
	/**
	 * 手机名字或型号
	 */
	private String phoneType;
	/**
	 * 登入方式 1.APP 2.web 3.PC
	 */
	private int loginMode;
	/**
	 * 用户ID
	 */
	private int userId;
	/**
	 * 登入时间戳
	 */
	private String loginTime;
	/**
	 * 生成token
	 */
	private String token;
	/**
	 * 状态 1.登入在线 2.登出离线 
	 */
	private int status;
	
	private String praise_channel;
	
	private String comment_channel;
	
	private String gift_channel;
	
	private String ip;
	
	private int country_id;
	
	private String create_time;
	
	private int id;
	
	/**
	 * 渠道ID
	 */
	private int channel_id;
	
	private String user_name;
	private String user_head;
	/**
	 * 第三方登入注册
	 */
	private int registerWay;
	
}
