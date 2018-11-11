package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class BindParam {
	private int user_id;
	private String user_phone;
	private String password;
	private String account;
	private int account_type;
	private String authCode;
	private int country_id;
	private String phoneCode;
	
	
}
