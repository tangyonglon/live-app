package com.douliao.controller.server.model;

import com.douliao.util.TimeFormat;

import lombok.Data;

@Data
public class RegisterParam {
	private int id;
	/**
	 * 码号归属地
	 */
	private String phoneCode;
	/**
	 * 手机号
	 */
	private String userPhone;
	/**
	 * 验证码
	 */
	private String authCode;
	/**
	 * 第三方账号
	 */
	private String userAccount;
	/**
	 * 密码
	 */
	private String userPassword;
	/**
	 * 确认密码
	 */
	private String confirmUserPassword;
	/**
	 * 创建时间
	 */
	private String userCreateTime=TimeFormat.getSimple();
	/**
	 * 国家编号
	 */
	private int country_id;
	/**
	 * 注册默认状态值
	 */
	private int status=1;
	
	private String praise_channel;
	
	private String comment_channel;
	
	private String gift_channel;
	
	private int registerWay;
	
}
